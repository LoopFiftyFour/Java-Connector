package com.loop54.model.request.parameters;

import java.util.List;

/** This class is used to specify which queries to get in a request. Used for paging and sorting. */
public class QueryCollectionParameters {
    /** How many queries to skip. If null, the engine defaults to 0. */
    public Integer skip;

    /** How many queries to take. If null, the engine defaults to 5. */
    public Integer take;

    /** How to sort the queries. Will default to relevance descending. */
    public List<QuerySortingParameter> sortBy;
}
