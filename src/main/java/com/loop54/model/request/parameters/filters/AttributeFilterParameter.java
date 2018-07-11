package com.loop54.model.request.parameters.filters;

/**
 * Used for filtering entities that have a certain attribute value.
 * @param <T> The type of the entity that we want to filter.
 */
public class AttributeFilterParameter<T> extends FilterParameter {

    /**
     * @param type Type of the filter. If you want to use {@link #FilterParameterType.ATTRIBUTE} it's easier to use the {@link #AttributeFilterParameter(String, Object)}"/> constructor.
     * @param value
     */
    public AttributeFilterParameter(FilterParameterType type, T value)
    {
        this.type = type;
        this.value = value;
        this.attributeName = null;
    }

    /**
     * Constructor. Use this if you want to use the filter parameter to filter on an attribute. Will set the type of
     * the filter to {@link #FilterParameterType.ATTRIBUTE}.
     * @param attributeName Name of the attribute to filter on. For instance "category". Note that the names of the
     *                      attributes vary depending on setup.
     * @param value The value to compare. For instance a category name if filtering on a category attribute.
     */
    public AttributeFilterParameter(String attributeName, T value)
    {
        this.type = FilterParameterType.ATTRIBUTE;
        this.attributeName = attributeName;
        this.value = value;
    }

    /**
     * Type of the filter. If the type of the filter is {@link #FilterParameterType.ATTRIBUTE}, the name of the attribute needs to be specified in the {@link #attributeName} field.
     */
    public final FilterParameterType type;

    /**
     * The name of attribute to filter on. This must be set if {@link #type} is {@link #FilterParameterType.ATTRIBUTE}.
     */
    public final String attributeName;

    /**
     * The value to test the attributes values against.
     */
    public T value;

    /**
     * How to compare the attribute value against the provided value. Defaults to {@link #FilterComparisonMode.EQUALS}.
     */
    public FilterComparisonMode comparisonMode = FilterComparisonMode.EQUALS;
}
