package com.loop54.model.request;

import com.loop54.model.request.parameters.EntityCollectionParameters;
import com.loop54.model.request.parameters.QueryCollectionParameters;

/** This class is used to configure a search request to the Loop54 e-commerce search engine. */
public class SearchRequest extends Request {
    /** The search query from the end-user. Can at most be 200 chars long. */
    public SearchRequest(String query) {
        this.query = query;
    }

    /** The search query from the end-user. Can at most be 200 chars long. */
    public final String query;

    /**
     * Parameters for specifying which direct results to retrieve. Such as filtering, faceting, sorting and paging.
     * Only affects the direct results, to modify the related results use {@link #relatedResultsOptions}.
     */
    public final EntityCollectionParameters resultsOptions = new EntityCollectionParameters();

    /**
     * Parameters for specifying which related results to retrieve. Such as filtering, faceting, sorting and paging.
     * Only affects the related results, to modify the direct results use {@link #resultsOptions}.
     */
    public final EntityCollectionParameters relatedResultsOptions = new EntityCollectionParameters();

    /** Parameters for specifying how spelling suggestions should be retrieved. Contains paging and sorting options. */
    public final QueryCollectionParameters spellingSuggestionsOptions = new QueryCollectionParameters();

    /** Parameters for specifying how related queries should be retrieved. Contains paging and sorting options. */
    public final QueryCollectionParameters relatedQueriesOptions = new QueryCollectionParameters();
}
