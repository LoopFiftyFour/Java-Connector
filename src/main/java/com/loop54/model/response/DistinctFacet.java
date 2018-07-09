package com.loop54.model.response;

import com.fasterxml.jackson.databind.JsonNode;
import com.loop54.model.FacetType;

import java.util.List;

/**
 * This class provides results for a distinct facet. A distinct facet consists of a finite number of
 * options with the number of connected entities as the values. Only entities connected to selected
 * facet options are returned. Or all if none are selected.
 */
public class DistinctFacet extends Facet {
    /** The type of the facet (as set in the request). */
    @Override public FacetType getType() { return FacetType.DISTINCT; }

    /** Whether or not the facet has any options. */
    @Override public boolean hasValues() { return !items.isEmpty(); }

    private List<DistinctFacetItem> items;

    /** The options found for this facet. */
    public List<DistinctFacetItem> getItems() { return items; }

    /** Represents a facet option found in the result set. */
    public static class DistinctFacetItem  {
        public Object item;

        /**
         * Gets the facet option as the type provided.
         *
         * @param <T> The type of the expected facet value.
         * @return The facet option as the type provided.
         */
        public <T> T getItem(Class<T> clazz) { return getValueOrNull(item, clazz); }

        /** Number of entities belonging to this option. */
        public int count;

        /** Whether or not this option was marked selected in the request. */
        public boolean selected;
    }
}
