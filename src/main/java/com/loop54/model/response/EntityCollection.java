package com.loop54.model.response;

import com.loop54.model.Entity;

import java.util.List;

/** A collection of entities returned by the Loop54 e-commerce search engine. */
public class EntityCollection {
    /** The total number of entities that are available to return after filtering and faceting but before paging. */
    public int count;

    /** The facets calculated for the result set. */
    public List<Facet> facets;

    /** The filtered, faceted, sorted and paged entities. */
    public List<Entity> items;

    public DistinctFacet getDistinctFacetByName(String name) {
        return (DistinctFacet)getFacetByName(name);
    }

    public RangeFacet getRangeFacetByName(String name) {
        return (RangeFacet)getFacetByName(name);
    }

    private Facet getFacetByName(String name) {
        if (facets != null)
            for (Facet facet : facets)
                if (name.equals(facet.getName()))
                    return facet;
        return null;
    }
}
