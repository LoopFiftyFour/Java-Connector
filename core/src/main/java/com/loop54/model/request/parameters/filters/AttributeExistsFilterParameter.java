package com.loop54.model.request.parameters.filters;

/**
 * Used for filtering entities that have a certain attribute, regardless of the value.
 */
public class AttributeExistsFilterParameter extends FilterParameter {
    /**
     * @param attributeName The attribute to check whether it exists or not. For instance "category". Note that the names of the attributes vary depending on setup.
     */
    public AttributeExistsFilterParameter(String attributeName) {
        this.attributeName = attributeName;
    }

    /**
     * The attribute to look for. For instance "category".
     */
    public final String attributeName;
}