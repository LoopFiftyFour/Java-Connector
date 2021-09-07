package com.loop54.model.request.parameters;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents the type of relation that would be done when calling "GetRelatedEntitiesRequest"
 */
public enum RelationKinds
{
    /** 
     * Return the entities that are on the same context as the requested entity
     */
    @JsonProperty("similar")
    SIMILAR,

    /** 
     * Return the entities that are usually 'purchased' together with the requested entity
     */
    @JsonProperty("complementary")
    COMPLEMENTARY
}
