package com.loop54.model.request;

import com.loop54.model.request.parameters.EntityCollectionParameters;
import com.loop54.model.request.parameters.QueryCollectionParameters;
import com.loop54.model.request.parameters.TypedEntityCollectionParameters;

import java.util.function.Consumer;

/** This class is used to configure a search request to the Loop54 e-commerce search engine. */
public class SearchRequest extends ContentRequest {
    /**
     * The search query from the end-user. Can at most be 200 chars long.
     * @param query The query to search for.
     */
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

    /**
     * Configures {@link #resultsOptions}, e.g. {@code request.withResults(r -> r.page(0, 20).distinctFacet("Category"))}.
     * @param configurator Applied to {@link #resultsOptions}.
     * @return This request, for chaining.
     */
    public SearchRequest withResults(final Consumer<EntityCollectionParameters> configurator) {
        configurator.accept(resultsOptions);
        return this;
    }

    /**
     * Configures {@link #relatedResultsOptions}.
     * @param configurator Applied to {@link #relatedResultsOptions}.
     * @return This request, for chaining.
     */
    public SearchRequest withRelatedResults(final Consumer<EntityCollectionParameters> configurator) {
        configurator.accept(relatedResultsOptions);
        return this;
    }

    /**
     * Configures {@link #spellingSuggestionsOptions}.
     * @param configurator Applied to {@link #spellingSuggestionsOptions}.
     * @return This request, for chaining.
     */
    public SearchRequest withSpellingSuggestions(final Consumer<QueryCollectionParameters> configurator) {
        configurator.accept(spellingSuggestionsOptions);
        return this;
    }

    /**
     * Configures {@link #relatedQueriesOptions}.
     * @param configurator Applied to {@link #relatedQueriesOptions}.
     * @return This request, for chaining.
     */
    public SearchRequest withRelatedQueries(final Consumer<QueryCollectionParameters> configurator) {
        configurator.accept(relatedQueriesOptions);
        return this;
    }

    @Override
    public SearchRequest withContentResults(final String entityType, final Consumer<TypedEntityCollectionParameters> configurator) {
        super.withContentResults(entityType, configurator);
        return this;
    }

    @Override
    public SearchRequest withCustomData(final String key, final Object value) {
        super.withCustomData(key, value);
        return this;
    }
}
