package com.loop54.model.request;

import com.loop54.model.request.parameters.EntityCollectionParameters;

import java.util.function.Consumer;

/** This class is used to perform a request to get personalized recommendations. */
public class GetRecommendedEntitiesRequest extends Request {
    
    /**
     * Parameters for specifying which recommendations results to retrieve and how to format them.
     */
    public final EntityCollectionParameters resultsOptions = new EntityCollectionParameters();

    /**
     * Configures {@link #resultsOptions}, e.g. {@code request.withResults(r -> r.page(0, 20).distinctFacet("Category"))}.
     * @param configurator Applied to {@link #resultsOptions}.
     * @return This request, for chaining.
     */
    public GetRecommendedEntitiesRequest withResults(final Consumer<EntityCollectionParameters> configurator) {
        configurator.accept(resultsOptions);
        return this;
    }

    @Override
    public GetRecommendedEntitiesRequest withCustomData(final String key, final Object value) {
        super.withCustomData(key, value);
        return this;
    }
}
