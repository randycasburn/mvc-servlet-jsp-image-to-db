# Store Images in PostGreSQL (Spring MVC Servlet)

## User Story

- **As a user** with a Spring MVC with servlet (not Spring Boot), JSP and PostgreSQL DB project
- **I want to be able to** use an HTML form to upload an image to my server
- **and** store the image a a PostgreSQL DB
- **and** retrieve and display the image on an HTML page

## Database

The database directory has a shell script that will automatically create a `thing` database with a `thing` table for your use.

## Required steps

1. In your `pom.xml` file add the commons-fileupload dependency
```xml
<dependency>
    <groupId>commons-fileupload</groupId>
    <artifactId>commons-fileupload</artifactId>
    <version>1.4</version>
</dependency>
```
2. `springmvc-servlet.xml` add a `MultipartResolver` bean
```xml
<bean id="multipartResolver" class="org.springframework.web.multipart.commons.CommonsMultipartResolver">
   	<property name="maxUploadSize" value="268435456"/>
</bean>
```

3. In your JSP form
- Add the encoding type to the form element
```html
<form:form class="form" action="thing" method="POST" modelAttribute="thingToUpdate" enctype="multipart/form-data">
```
- Add a file input
```html
<input type="file" class="form-control" id="avatarContainer" name="avatarContainer" value=""/>
```
4. In your Model, include a `byte[]`  field
```java
private byte[] avatar;
```
5. In your DB, add a column of type `bytea`

```sql
alter table thing alter column avatar type bytea using avatar::bytea;
```
6. In your DAO add your new DB field to your update statement and your row mapper
- For `ResultSet` objects from `jdbcTemplate.query()` 
```java
d.setAvatar(resultSet.getBytes("avatar"));

```
- For `SqlRowSet` objects from `jdbcTemplate.queryForRowSet()`
```java
user.setAvatar((byte[]) results.getObject("avatar"));
```

7. In your controller:

- Add the `consumes` setting to your `@ParamMapping` annotation
```java
@RequestMapping(value = "/thing", method = RequestMethod.POST, consumes = {MULTIPART_FORM_DATA_VALUE})
```
- Add a parameter to your controller action method for a MultipartFile
```java
@RequestParam("avatarContainer") MultipartFile avatarContainer
```
- Set the Model `byte[]` to the image provided in the MultipartFile object

```java
thing.setAvatar(avatarContainer.getBytes())
```

- Here is the entire controller post method:

```java
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
        thingDao.updateThing(thing);
        return "redirect:/things";
    }
```
- Add a method to your controller that will return an image. Use the URL as the `src` attribute of any `<img>` element
```java
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

```
