package com.loop54.model.request.parameters;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/** This class is used to specify which queries to get in a request. Used for paging and sorting. */
public class QueryCollectionParameters {
    /** How many queries to skip. If null, the engine defaults to 0. */
    public Integer skip;

    /** How many queries to take. If null, the engine defaults to 5. */
    public Integer take;

    /** How to sort the queries. Will default to relevance descending. */
    public List<QuerySortingParameter> sortBy;

    /**
     * Sets how the queries should be paged.
     * @param skip How many queries to skip.
     * @param take How many queries to take.
     * @return These parameters, for chaining.
     */
    public QueryCollectionParameters page(final int skip, final int take) {
        this.skip = skip;
        this.take = take;
        return this;
    }

    /**
     * Appends sorting parameters. The first sorting parameter added specifies the primary order.
     * @param sortingParameters The sorting parameters to append.
     * @return These parameters, for chaining.
     */
    public QueryCollectionParameters sortBy(final QuerySortingParameter... sortingParameters) {
        if (sortBy == null) {
            sortBy = new ArrayList<>();
        }

        sortBy.addAll(Arrays.asList(sortingParameters));
        return this;
    }
}
