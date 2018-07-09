package com.loop54.model.request.parameters.facets;

import com.loop54.model.FacetType;

/** Used for faceting an entity collection based on the attributes of the entities. */
public abstract class FacetParameter {
    protected FacetParameter(String attributeName) {
        this.attributeName = attributeName;
    }

    /** Name of the facet to return to the client. Can be a 'friendly' name to show to the user. If not set the {@link #attributeName} will be used. */
    public String name;

    /** The name of the attribute on the entities to facet the result on. If the attribute does not exist on any entity, all entities will pass and an empty facet will be returned. */
    public final String attributeName;

    /** Type of the facet. Range or Distinct. */
    public abstract FacetType getType();
}
