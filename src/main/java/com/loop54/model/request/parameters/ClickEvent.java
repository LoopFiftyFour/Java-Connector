package com.loop54.model.request.parameters;

import com.loop54.model.Entity;
import com.loop54.model.request.parameters.Event;

/**
 * Event for when a user has clicked on a product.
 */
public class ClickEvent extends Event {

    /**
     * @param entity The entity that the user has clicked on.
     */
    public ClickEvent(Entity entity) {
        super(Event.CLICK, entity);
    }
}
