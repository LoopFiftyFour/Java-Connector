package com.loop54.model.request;

import com.loop54.model.request.parameters.EntityCollectionParameters;

/** This class is used to configure a GetEntities request to the Loop54 e-commerce search engine. */
public class GetEntitiesRequest extends Request {
    /**
     * Parameters for specifying which entities to retrieve. Such as filtering, faceting, sorting and paging.
     * Note that filtering is advised when doing this request.
     */
    public final EntityCollectionParameters resultsOptions = new EntityCollectionParameters();
}
