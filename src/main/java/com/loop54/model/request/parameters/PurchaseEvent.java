package com.loop54.model.request.parameters;

import com.loop54.model.Entity;

/**
 * Event for when a user has purchased a product.
 */
public class PurchaseEvent extends Event {

    /**
     * @param entity The entity that the user has purchased.
     */
    public PurchaseEvent(Entity entity) {
        super(Event.PURCHASE, entity);
    }

    /**
     * An identifier of the purchase order. Optional.
     */
    public String orderId;

    /**
     * How much revenue the purchase may have resulted in. Optional.
     */
    public Double revenue;

    /**
     * How many items of the entity that has been purchased. Optional.
     */
    public Integer quantity;
}
