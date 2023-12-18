package com.loop54.model.request;

import com.loop54.model.request.parameters.EntityCollectionParameters;

/** This class is used to perform a request to get personalized recommendations. */
public class GetRecommendedEntitiesRequest extends Request {
    
    /**
     * Parameters for specifying which recommendations results to retrieve and how to format them.
     */
    public final EntityCollectionParameters resultsOptions = new EntityCollectionParameters();
}