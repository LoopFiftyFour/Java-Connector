package com.loop54.model.response;

/** The result of a suggest request. */
public class SuggestResponse extends Response {
    /** Whether the engine understood the search query. */
    public boolean makesSense;

    /** The (normalized) original query typed by the user. */
    public String originalQuery;

    /** The query used by the engine for the search request. It may either be the original query, or the top query suggestion. */
    public String queryUsedForSearch;

    /** The query that the engine considered most relevant, will get a result with scopes. */
    public ScopedQueryResult scopedQuery;

    /** A collection of query suggestions for the request query. */
    public QueryCollection queries;

    /** The results that match the query. */
    public EntityCollection results;
}
