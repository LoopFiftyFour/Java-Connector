package com.loop54.model.request;

import com.loop54.model.request.parameters.EntityCollectionParameters;
import com.loop54.model.request.parameters.QueryCollectionParameters;

import java.util.function.Consumer;

/** This class is used for performing suggest requests to the Loop54 e-commerce search engine. */
public class SuggestRequest extends ContentRequest {
    /**
     * The partial query that the user has entered.
     * @param query the query to get auto complete suggestions for.
     */
    public SuggestRequest(String query) {
        this(query, false);
    }

    /**
     * The partial query that the user has entered.
     * @param query the query to get auto complete suggestions for.
     * @param useTopSuggestionForSearch When 'true', the search may run against the top suggested autocompletion for the input query.
     */
    public SuggestRequest(String query, boolean useTopSuggestionForSearch) {
        this.query = query;
        this.useTopSuggestionForSearch = useTopSuggestionForSearch;
    }

    /** The partial query that the user has entered. */
    public final String query;

    /** When 'true', the search may run against the top suggested autocompletion for the input query. */
    public final boolean useTopSuggestionForSearch;

    /** Parameters for specifying how the suggest results should be retrieved. Contains paging and sorting options. */
    public final QueryCollectionParameters queriesOptions = new QueryCollectionParameters();

    /**
     * Parameters for specifying which direct results to retrieve. Such as filtering, faceting, sorting and paging.
     */
    public final EntityCollectionParameters resultsOptions = new EntityCollectionParameters();

    /**
     * Configures {@link #queriesOptions}, e.g. {@code request.withQueries(q -> q.page(0, 10))}.
     * @param configurator Applied to {@link #queriesOptions}.
     * @return This request, for chaining.
     */
    public SuggestRequest withQueries(final Consumer<QueryCollectionParameters> configurator) {
        configurator.accept(queriesOptions);
        return this;
    }

    /**
     * Configures {@link #resultsOptions}, e.g. {@code request.withResults(r -> r.page(0, 20).distinctFacet("Category"))}.
     * @param configurator Applied to {@link #resultsOptions}.
     * @return This request, for chaining.
     */
    public SuggestRequest withResults(final Consumer<EntityCollectionParameters> configurator) {
        configurator.accept(resultsOptions);
        return this;
    }

    @Override
    public SuggestRequest withCustomData(final String key, final Object value) {
        super.withCustomData(key, value);
        return this;
    }
}
