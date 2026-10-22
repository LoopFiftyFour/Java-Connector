package com.loop54.model.request;

import com.loop54.model.Entity;
import com.loop54.model.request.parameters.EntityCollectionParameters;

import java.util.List;
import java.util.function.Consumer;

/** This class is used to configure a GetBasketRecommendations request to the Loop54 e-commerce search engine. */
public class GetBasketRecommendationsRequest extends Request {

    /**
     * @param entities The entities to use in the query.
     */
    public GetBasketRecommendationsRequest(List<Entity> entities) {
        if (entities == null)
            throw new IllegalArgumentException("entities was null");

        this.entities = entities;
    }

    /**
     * The set of entities in the basket to get recommendations for.
     */
    public List<Entity> entities;

    /**
     * Parameters for specifying which entities to retrieve. Such as filtering, faceting, sorting and paging.
     */
    public final EntityCollectionParameters resultsOptions = new EntityCollectionParameters();

    /**
     * Configures {@link #resultsOptions}, e.g. {@code request.withResults(r -> r.page(0, 20).distinctFacet("Category"))}.
     * @param configurator Applied to {@link #resultsOptions}.
     * @return This request, for chaining.
     */
    public GetBasketRecommendationsRequest withResults(final Consumer<EntityCollectionParameters> configurator) {
        configurator.accept(resultsOptions);
        return this;
    }

    @Override
    public GetBasketRecommendationsRequest withCustomData(final String key, final Object value) {
        super.withCustomData(key, value);
        return this;
    }
}
