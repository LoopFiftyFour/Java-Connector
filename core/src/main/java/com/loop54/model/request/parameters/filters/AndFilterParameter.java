package com.loop54.model.request.parameters.filters;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Used for combining two or more filters using AND-logic
 */
public class AndFilterParameter extends FilterParameter {

    public AndFilterParameter() {
    }

    /**
     * @param filters Filters to combine using AND logic
     */
    public AndFilterParameter(FilterParameter... filters) {
        for(FilterParameter filter : filters)
            and.add(filter);
    }

    /**
     * The filters that should be combined.
     */
    public final List<FilterParameter> and = new ArrayList<>();

    /**
     * Appends filters to combine using AND logic.
     * @param filters The filters to append.
     * @return This filter, for chaining.
     */
    public AndFilterParameter and(final FilterParameter... filters) {
        and.addAll(Arrays.asList(filters));
        return this;
    }
}
