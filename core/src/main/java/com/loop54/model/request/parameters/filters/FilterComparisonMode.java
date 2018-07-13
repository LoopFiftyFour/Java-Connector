package com.loop54.model.request.parameters.filters;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * How values should be compared to the attribute values of the entities being filtered.
 */
public enum FilterComparisonMode
{
    @JsonProperty("equals")
    EQUALS,

    @JsonProperty("greaterThan")
    GREATER_THAN,

    @JsonProperty("greaterThanOrEquals")
    GREATER_THAN_OR_EQUALS,

    @JsonProperty("lessThan")
    LESS_THAN,

    @JsonProperty("lessThanOrEquals")
    LESS_THAN_OR_EQUALS,

    /**
     * Only available if filtering on strings.
     */
    @JsonProperty("contains")
    CONTAINS
}
