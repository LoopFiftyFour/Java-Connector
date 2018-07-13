package com.loop54.model.request.parameters.facets;

import com.loop54.model.FacetType;

public class RangeFacetParameter<T> extends FacetParameter {
    public RangeFacetParameter(String attributeName, RangeFacetSelectedParameter<T> selected, String name) {
        super(attributeName);
        this.selected = selected;
        this.name = name;
    }

    /** @return The type of the facet parameter. Will always be {@link FacetType#RANGE} */
    @Override public FacetType getType() { return FacetType.RANGE; }

    /** The min and max values selected by the user. */
    public RangeFacetSelectedParameter<T> selected;

    /**
     * The selected min and max for the facet.
     * @param <T> Type of the attribute to facet on.
     */
    public static class RangeFacetSelectedParameter<T>
    {
        /** Selected minimum. */
        public T min;

        /** Selected maximum. */
        public T max;
    }
}
