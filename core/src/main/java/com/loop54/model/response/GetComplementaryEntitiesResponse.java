package com.loop54.model.response;

/** The result of a getComplementaryEntities request. */
public class GetComplementaryEntitiesResponse extends Response {
    /** The entities that are complementary to the entity supplied in the request. */
    public EntityCollection results;
}
