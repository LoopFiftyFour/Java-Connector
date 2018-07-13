package com.loop54.model.response;

/** The result of a request to get entities with an attribute (for instance category). */
public class GetEntitiesByAttributeResponse extends Response {
    /** The entities that are connected to the attribute. */
    public EntityCollection results;
}
