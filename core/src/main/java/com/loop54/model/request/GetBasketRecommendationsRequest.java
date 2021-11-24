package com.loop54.model.request;

import com.loop54.model.Entity;
import java.util.List;
import com.loop54.model.request.parameters.EntityCollectionParameters;

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
}
