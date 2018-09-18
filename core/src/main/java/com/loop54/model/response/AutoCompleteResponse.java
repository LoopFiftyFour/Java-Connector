package com.loop54.model.response;

/** The result of an autocomplete request. */
public class AutoCompleteResponse extends Response {

    /** The query that the engine considered most relevant, will get a result with scopes. */
    public ScopedQueryResult scopedQuery;

    /** A collection of query suggestions for the request query. */
    public QueryCollection queries;
}
