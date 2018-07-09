package com.loop54.model.response;

/** The result of a search operation. */
public class SearchResponse extends Response {
    /** Whether or not the engine understood the search query. */
    public boolean makesSense;

    /** The query made in the search request. */
    public String query;

    /** A collection of suggestions for alternate spellings of the query. */
    public QueryCollection spellingSuggestions;

    /** A collection of suggestions for queries that are related to the provided query. */
    public QueryCollection relatedQueries;

    /** The results that match the query. */
    public EntityCollection results;

    /** Any additional results that, while not matching, are relevant to the query. */
    public EntityCollection relatedResults;
}
