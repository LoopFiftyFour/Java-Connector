package com.loop54.model.request.parameters.filters;

import java.util.ArrayList;
import java.util.List;

/**
 * Used for combining two or more filters using or logic
 */
public class OrFilterParameter extends FilterParameter {
    public OrFilterParameter() {
    }

    /**
     * @param filters Filters to combine using OR logic
     */
    public OrFilterParameter(FilterParameter... filters) {
        for (FilterParameter filter : filters)
            or.add(filter);
    }

    /**
     * The filters that should be combined.
     */
    public final List<FilterParameter> or = new ArrayList<>();
}
