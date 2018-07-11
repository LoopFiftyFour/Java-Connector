package com.loop54.model.request.parameters;

import com.loop54.model.Entity;

/**
 * Event for when a user has purchased a product.
 */
public class CustomEvent extends Event {

    /**
     * @param entity The entity that the user has purchased.
     */
    public CustomEvent(String type, Entity entity) { super(type, entity); }
}
