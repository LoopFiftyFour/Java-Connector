package com.loop54.model.request;

import com.loop54.model.Entity;
import com.loop54.model.request.parameters.EntityCollectionParameters;

/** This class is used to configure a GetEntities request to the Loop54 e-commerce search engine. */
public class GetRelatedEntitiesRequest extends Request {

    /**
     * @param entityType Type of the entity to query
     * @param entityId Id of the entity to query
     */
    public GetRelatedEntitiesRequest(String entityType, String entityId)
    {
        if (entityType == null)
            throw new IllegalArgumentException("entityType was null");

        if (entityId == null)
            throw new IllegalArgumentException("entityId was null");

        this.entity = new Entity(entityType, entityId);
    }

    /**
     * @param entity The entity to use in the query.
     */
    public GetRelatedEntitiesRequest(Entity entity)
    {
        if (entity == null)
            throw new IllegalArgumentException("entity was null");

        this.entity = entity;
    }

    /**
     * The entity who's related entities we want to get. Only needs the type and id set.
     */
    public Entity entity;

    /**
     * Parameters for specifying which entities to retrieve. Such as filtering, faceting, sorting and paging.
     * Note that filtering is advised when doing this request.
     */
    public final EntityCollectionParameters resultsOptions = new EntityCollectionParameters();
}
