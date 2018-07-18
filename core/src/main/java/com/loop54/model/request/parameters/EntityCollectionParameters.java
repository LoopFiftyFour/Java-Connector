package com.loop54.model.request.parameters;

import com.loop54.model.FacetType;
import com.loop54.model.request.parameters.facets.DistinctFacetItemSortingParameter;
import com.loop54.model.request.parameters.facets.DistinctFacetParameter;
import com.loop54.model.request.parameters.facets.FacetParameter;
import com.loop54.model.request.parameters.facets.RangeFacetParameter;
import com.loop54.model.request.parameters.filters.FilterParameter;

import java.util.ArrayList;
import java.util.List;

/** This class specifies how a collection of entities should be paged, filtered, sorted and faceted. */
public class EntityCollectionParameters {
    /** How many entities to skip when paging the result. If null it defaults to 0. */
    public Integer skip;

    /** How many entities to take when paging the result. If null it defaults to 5. */
    public Integer take;

    /**
     * List of sorting parameters specifying how the result should be sorted. The first sorting parameter specifies the
     * primary order. If items are equal, they will be sorted using the second parameter, and so on. If not set the entities will be sorted by relevance descending.
     */
    public List<EntitySortingParameter> sortBy;

    /** List of facets to calculate and possibly filter the result on. If not set, no faceting is done. */
    public List<FacetParameter> facets;

    /** How the results should be filtered. If not set, no filtering is done. */
    public FilterParameter filter;

    /**
     * Adds a facet with the type {@link FacetType#DISTINCT} to the entity collection parameter.
     * @param attributeName What attribute on the entities to facet on. This must match one of the attributes available on the entities in the search engine.
     */
    public void addDistinctFacet(String attributeName) {
        addDistinctFacet(attributeName, null, null, null);
    }

    /**
     * Adds a facet with the type {@link FacetType#DISTINCT} to the entity collection parameter.
     * @param attributeName What attribute on the entities to facet on. This must match one of the attributes available on the entities in the search engine.
     * @param selected Values selected by the user. If none are selected this can be null.
     */
    public void addDistinctFacet(String attributeName, List<?> selected) {
        addDistinctFacet(attributeName, selected, null, null);
    }

    /**
     * Adds a facet with the type {@link FacetType#DISTINCT} to the entity collection parameter.
     * @param attributeName What attribute on the entities to facet on. This must match one of the attributes available on the entities in the search engine.
     * @param selected Values selected by the user. If none are selected this can be null.
     * @param name The desired name of the facet in the response. Will be the same as the attributeName if null.
     * @param sortBy How to sort the facet options in the response.
     */
    public void addDistinctFacet(String attributeName, List<?> selected, String name, List<DistinctFacetItemSortingParameter> sortBy) {
        if (facets == null)
            facets = new ArrayList<>();

        facets.add(new DistinctFacetParameter<>(attributeName, selected, name, sortBy));
    }


    /**
     * Adds a facet with the type {@link FacetType#RANGE} to the entity collection parameter.
     * @param attributeName What attribute on the entities to facet on. This must match one of the attributes available on the entities in the search engine.
     */
    public void addRangeFacet(String attributeName) {
        addRangeFacet(attributeName, null, null);
    }

    /**
     * Adds a facet with the type {@link FacetType#RANGE} to the entity collection parameter.
     * @param attributeName What attribute on the entities to facet on. This must match one of the attributes available on the entities in the search engine.
     * @param selected The min and max values selected by the user. Can be left to null if nothing is selected.
     * @param name The desired name of the facet in the response. Will be the same as the attributeName if null.
     */
    public void addRangeFacet(String attributeName, RangeFacetParameter.RangeFacetSelectedParameter<?> selected, String name) {
        if (facets == null)
            facets = new ArrayList<>();

        facets.add(new RangeFacetParameter<>(attributeName, selected, name));
    }
}
