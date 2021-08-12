package com.company.controller;


import com.company.dao.ThingDao;
import com.company.model.Thing;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.ResourceUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.awt.image.BufferedImage;
import java.io.IOException;

import static org.springframework.util.MimeTypeUtils.MULTIPART_FORM_DATA_VALUE;

@Controller
public class ThingController {

    @Autowired
    ThingDao thingDao;


    @RequestMapping(path={"/", "/things"})
    public String showThingsPage(@RequestParam(required = false) String update, ModelMap model) {
        if (update != null) {
            model.put("update", update);
        }
        model.put("things", thingDao.getAllThings());
        return "thing";
    }

    @RequestMapping(path = "/thing", method = RequestMethod.GET)
    public String showThingForm(@RequestParam Long id, @RequestParam String action, ModelMap model) {
        model.put("thingToUpdate", new Thing());
        if(action.equals("update") && id != null) {
            model.put("thingToUpdate", thingDao.getThing(id));
            return "thingUpdateForm";
        } else if(action.equals("delete") && id != null) {
            thingDao.deleteThing(id);
            return "redirect:/things";
        } else if(action.equals("add")) {
            return "thingUpdateForm";
        }
        return "redirect:/things";
    }

    @RequestMapping(path = "/thing/image", method = RequestMethod.GET)
    public ResponseEntity<byte[]> getThingImage(@RequestParam("id") Long id) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        BufferedImage img;
        headers.setContentType(MediaType.IMAGE_PNG);
        headers.setCacheControl(CacheControl.noCache().getHeaderValue());
        byte[] media = thingDao.getThing(id).getAvatar();
        if (media == null) {
            media = FileUtils.readFileToByteArray(ResourceUtils.getFile("classpath:../../img/150.png"));
        }
        return new ResponseEntity<>(media, headers, HttpStatus.OK);
    }

    @RequestMapping(value = "/thing", method = RequestMethod.POST, consumes = {MULTIPART_FORM_DATA_VALUE})
    public String handleUpdateThing(@Valid @ModelAttribute("thing") Thing thing, BindingResult result, @RequestParam("avatarContainer") MultipartFile avatarContainer, ModelMap model) throws IOException {
        if (result.hasErrors()) {
            model.put("thingToUpdate", thing);
            model.put("error", "Name cannot be empty.");
            return "thingUpdateForm";
        }
        if(avatarContainer.isEmpty()) {
            thing.setAvatar(null);
        } else {
            thing.setAvatar(avatarContainer.getBytes());
        }
        if(thing.getId() == null) {
            thingDao.addThing(thing);
        } else {
            thingDao.updateThing(thing);
        }
        return "redirect:/things";
    }
}
