package com.loop54.model.request.parameters;

import com.fasterxml.jackson.annotation.JsonProperty;

/** This class is used to specify how to sort entities in a response from the Loop54 e-commerce search engine. */
public class EntitySortingParameter {
    public EntitySortingParameter() { }

    /** @param type How to order the entities. */
    public EntitySortingParameter(Types type) {
        this.type = type;
    }

    /**
     * Used when wanting to order on a certain attribute on the entities.
     * @param attributeName What attribute to sort on. For instance "category" or "price". Note that the attributes in your environment might vary.
     */
    public EntitySortingParameter(String attributeName)
    {
        this.type = Types.ATTRIBUTE;
        this.attributeName = attributeName;
    }

    /** How the entities should be sorted. */
    public Types type;

    /** If choosing to sort by {@link Types#ATTRIBUTE} this property specifies which attribute to sort by. */
    public String attributeName;

    /** In what order to sort the entities. */
    public SortOrders order;

    public enum Types {
        /** Sort the entities by a specific attribute that exist in your catalogue. For instance "price". */
    	@JsonProperty("attribute")
        ATTRIBUTE,

        /** Sort the entities by id. */
        @JsonProperty("id")
        ID,

        /** Sort the entities by type. Type can vary. But in most cases it is "Product" but it can also be "Content" depending on setup. */
        @JsonProperty("type")
        TYPE,

        /** Sort the entities by their relevance. For instance the context of a search hit. */
        @JsonProperty("relevance")
        RELEVANCE,

        /** Sort the entities by their popularity. */
        @JsonProperty("popularity")
        POPULARITY,
    }
}
