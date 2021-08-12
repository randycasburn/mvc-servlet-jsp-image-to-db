package com.company.dao;

import com.company.model.Thing;

import java.io.IOException;
import java.util.List;

public interface ThingDao {

    /**
     * Get a thing from the datastore that belongs to the given id.
     * If the id is not found, return null.
     *
     * @param thingId the thing id to get from the datastore
     * @return a filled out thing object
     */
    public Thing getThing(Long thingId);

    /**
     * Get all things from the datastore.
     *
     * @return all things as Thing objects in a List
     */
    public List<Thing> getAllThings();

    /**
     * Update a thing to the datastore. Only called on things that
     * are already in the datastore.
     *
     * @param updatedThing the thing object to update
     */
    public void updateThing(Thing updatedThing) throws IOException;

    void addThing(Thing thing);

    void deleteThing(Long id);

}
