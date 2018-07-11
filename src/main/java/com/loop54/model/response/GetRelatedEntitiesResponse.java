package com.loop54.model.response;

/** The result of a getRelatedEntities request. */
public class GetRelatedEntitiesResponse extends Response {
    /** The entities that are related to the entity supplied in the request. */
    public EntityCollection results;
}
