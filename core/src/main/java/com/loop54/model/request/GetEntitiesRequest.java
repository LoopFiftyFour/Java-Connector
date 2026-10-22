package com.loop54.model.request;

import com.loop54.model.request.parameters.EntityCollectionParameters;

import java.util.function.Consumer;

/** This class is used to configure a GetEntities request to the Loop54 e-commerce search engine. */
public class GetEntitiesRequest extends Request {
    /**
     * Parameters for specifying which entities to retrieve. Such as filtering, faceting, sorting and paging.
     * Note that filtering is advised when doing this request.
     */
    public final EntityCollectionParameters resultsOptions = new EntityCollectionParameters();

    /**
     * Configures {@link #resultsOptions}, e.g. {@code request.withResults(r -> r.page(0, 20).distinctFacet("Category"))}.
     * @param configurator Applied to {@link #resultsOptions}.
     * @return This request, for chaining.
     */
    public GetEntitiesRequest withResults(final Consumer<EntityCollectionParameters> configurator) {
        configurator.accept(resultsOptions);
        return this;
    }

    @Override
    public GetEntitiesRequest withCustomData(final String key, final Object value) {
        super.withCustomData(key, value);
        return this;
    }
}
