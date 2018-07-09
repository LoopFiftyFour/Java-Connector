package com.loop54.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.loop54.model.FacetType;
import com.loop54.model.ModelUtils;

/** Base class containing facet information returned by the Loop54 e-commerce search engine. */
@JsonIgnoreProperties({ "type" })
public abstract class Facet {
    public String name;

    /** The name of the facet (as set in the request). If name is not specified in the request parameter this will be the requested attributeName. */
    public String getName() { return name; }

    /** The type of the facet (as set in the request). */
    public abstract FacetType getType();

    /** Whether or not the facet have any options. */
    public abstract boolean hasValues();

    static <T> T getValueOrNull(Object node, Class<T> clazz) {
        if (node == null)
            return null;

        return ModelUtils.numberSafeCast(node, clazz);
    }
}
