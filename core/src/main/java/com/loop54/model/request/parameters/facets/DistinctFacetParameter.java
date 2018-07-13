package com.loop54.model.request.parameters.facets;

import com.loop54.model.FacetType;

import java.util.List;

/**
 * Used for distinct faceting of entities. A distinct facet consists of a finite number of
 * options with the number of connected entities as the values. Only entities connected to selected
 * facet options are returned. Or all if none are selected.
 *
 * @param <T> Type of the attribute to facet on.
 */
public class DistinctFacetParameter<T> extends FacetParameter {
    public DistinctFacetParameter(String attributeName, List<T> selected, String name, List<DistinctFacetItemSortingParameter> sortBy) {
        super(attributeName);
        this.selected = selected;
        this.name = name;
        this.sortBy = sortBy;
    }

    /** @return The type of the facet parameter. Will always be {@link FacetType#DISTINCT} */
    @Override public FacetType getType() { return FacetType.DISTINCT; }

    /** What options the user has selected. Only entities belonging to those options will be returned. */
    public final List<T> selected;

    /** These sorting parameters specify how the options are to be sorted in the response. */
    public final List<DistinctFacetItemSortingParameter> sortBy;
}
