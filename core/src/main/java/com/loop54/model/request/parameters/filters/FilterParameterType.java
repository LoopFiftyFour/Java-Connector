package com.loop54.model.request.parameters.filters;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Specifies the type of a attribute filter.
 */
public enum FilterParameterType {
    /**
     * Compares against an attribute on the entity
     */
    @JsonProperty("attribute")
    ATTRIBUTE,

    /**
     * Compares against the type of the entity
     */
    @JsonProperty("type")
    TYPE,

    /**
     * Compares against the id of the entity
     */
    @JsonProperty("id")
    ID,
}