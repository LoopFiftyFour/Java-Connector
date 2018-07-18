package com.loop54.model.request.parameters;

import com.loop54.model.Entity;

/**
 * Event for when a user has purchased a product.
 */
public class CustomEvent extends Event {

    /**
     * @param type Custom type string for the event. For example "Facebook_Like" or another interaction from the user.
     * @param entity The entity that the user has interacted with.
     */
    public CustomEvent(String type, Entity entity) { super(type, entity); }
}
