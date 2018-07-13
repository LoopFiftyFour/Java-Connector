package com.loop54.model.request.parameters.filters;

/**
 * Used for inverting a filter.
 */
public class InverseFilterParameter extends FilterParameter {
    /**
     * @param not The filter to inverse. Meaning if that filter results in false the inverse will be true.
     */
    public InverseFilterParameter(FilterParameter not)
    {
        this.not = not;
    }

    /**
     * The filter that should be inversed.
     */
    public final FilterParameter not;
}
