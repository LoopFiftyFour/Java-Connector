package com.loop54.model.request.parameters;

import com.loop54.model.Entity;

/**
 * Event for when a user has added a product to the shopping cart.
 */
public class AddToCartEvent extends Event {

    /**
     * @param entity The entity that the user has added to the cart.
     */
    public AddToCartEvent(Entity entity) {
        super(Event.ADD_TO_CART, entity);
    }
}
