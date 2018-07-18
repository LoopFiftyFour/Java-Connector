package com.loop54.model.response;

import com.loop54.model.FacetType;

/**
 * This class provides results for a range facet. A facet that has a min and max value and only entities
 * in between them are returned.
 */
public class RangeFacet extends Facet {
    public Object min;

    public Object max;

    public Object selectedMin;

    public Object selectedMax;

    /** The type of the facet (as set in the request). */
    @Override
    public FacetType getType() { return FacetType.RANGE; }

    /** Whether or not the facet has any options. */
    @Override
    public boolean hasValues() { return min != null || max != null; }

    /**
     * Gets the minimum value of the facet.
     * @param <T> The type of the expected facet value.
     * @param clazz Type of the values in the facet. Must match the type of the attribute faceted on.
     * @return Minimum value of the facet.
     */
    public <T> T getMin(Class<T> clazz) { return getValueOrNull(min, clazz); }

    /**
     * Gets the maximum value of the facet.
     * @param <T> The type of the expected facet value.
     * @param clazz Type of the values in the facet. Must match the type of the attribute faceted on.
     * @return Maximum value of the facet.
     */
    public <T> T getMax(Class<T> clazz) { return getValueOrNull(max, clazz); }

    /**
     * Gets the minimum selected value of the facet, as provided in the request.
     * @param <T> The type of the expected facet value.
     * @param clazz Type of the values in the facet. Must match the type of the attribute faceted on.
     * @return Minimum selected value of the facet.
     */
    public <T> T getSelectedMin(Class<T> clazz) { return getValueOrNull(selectedMin, clazz); }

    /**
     * Gets the minimum selected value of the facet, as provided in the request.
     * @param <T> The type of the expected facet value.
     * @param clazz Type of the values in the facet. Must match the type of the attribute faceted on.
     * @return Minimum selected value of the facet.
     */
    public <T> T getSelectedMax(Class<T> clazz) { return getValueOrNull(selectedMax, clazz); }
}
