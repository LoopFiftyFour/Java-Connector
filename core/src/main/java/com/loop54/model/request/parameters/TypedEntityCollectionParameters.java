package com.loop54.model.request.parameters;

import com.loop54.exceptions.Loop54ArgumentException;

/**
 * Specifies how a collection of entities of a specific entity type should be paged, filtered, sorted and faceted.
 * Used for Content Search, where each content entity type (e.g. "Brand", "Article") has its own options.
 */
public class TypedEntityCollectionParameters extends EntityCollectionParameters {
    /**
     * @param entityType The content entity type to search, e.g. "Brand". Case-sensitive.
     */
    public TypedEntityCollectionParameters(String entityType) {
        if (entityType == null || entityType.isBlank())
            throw new Loop54ArgumentException("Entity type must be set.");

        this.entityType = entityType;
    }

    /**
     * The content entity type to search, e.g. "Brand" or "Article". Case-sensitive.
     * Search, paging, sorting, faceting and filtering are scoped to this entity type only.
     */
    public final String entityType;
}
