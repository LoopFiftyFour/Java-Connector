package com.loop54.model;

/** Represent the two types of facets available to make. */
public enum FacetType {
    /**
     * A facet should consist of a finite number of options with the number of connected entities as the values.
     * Only entities connected to selected facet options are returned. Or all if none are selected.
     */
    DISTINCT,

    /** A facet that has a min and max value and only entities in between them are returned. */
    RANGE,
}
