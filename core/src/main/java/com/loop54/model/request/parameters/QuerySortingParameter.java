package com.loop54.model.request.parameters;

import com.fasterxml.jackson.annotation.JsonProperty;

/** This class specifies how queries should be sorted when making a request. */
public class QuerySortingParameter {
    public QuerySortingParameter() { }

    /** @param type How to sort the queries. */
    public QuerySortingParameter(Types type) {
        this.type = type;
    }

    /**
     * @param type How to sort the queries.
     * @param order In what order to sort the queries.
     */
    public QuerySortingParameter(Types type, SortOrders order) {
        this(type);
        this.order = order;
    }

    /** How the queries should be sorted. */
    public Types type;

    /** In what order to sort the queries. */
    public SortOrders order;

    /** The types of sorting available when sorting queries. */
    public enum Types {
        @JsonProperty("relevance")
        RELEVANCE,

        @JsonProperty("popularity")
        POPULARITY,

        @JsonProperty("alphabetic")
        ALPHABETIC,
    }
}
