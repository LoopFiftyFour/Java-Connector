package com.loop54.model.request.parameters;

import com.loop54.model.Entity;

/**
 * Base event class. This represents a user interaction on a product (or as we call it, entity).
 */
public abstract class Event {

    /**
     * The string value corresponding to the click-event
     */
    public static final String CLICK = "click";

    /**
     * The string value corresponding to the addtocart-event
     */
    public static final String ADD_TO_CART = "addtocart";

    /**
     * The string value corresponding to the purchase-event
     */
    public static final String PURCHASE = "purchase";

    protected Event(String type, Entity entity)
    {
        this.type = type;
        this.entity = entity;
    }

    /**
     * Type of the event.
     */
    public final String type;

    /**
     * The entity that the user has interacted with.
     */
    public final Entity entity;
}
