package com.loop54.model.request.parameters.facets;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.loop54.model.request.parameters.SortOrders;

/** Describes how to sort the items returned in the facet. */
public class DistinctFacetItemSortingParameter {
    public DistinctFacetItemSortingParameter() { }

    /**
     * @param type How the sorting is done.
     * @param order Whether to sort items descending or ascending.
     */
    public DistinctFacetItemSortingParameter(final Types type, final SortOrders order) {
        this.type = type;
        this.order = order;
    }

    /** How the sorting is done. */
    public Types type;

    /** Whether to sort items descending or ascending. */
    public SortOrders order;

    public enum Types
    {
    	@JsonProperty("item")
        ITEM,
        @JsonProperty("count")
        COUNT,
        @JsonProperty("selected")
        SELECTED,
    }
}
