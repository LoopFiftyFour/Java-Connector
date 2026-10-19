package com.loop54.model.response;

import java.util.List;

/** Base class for responses that can contain Content Search results. */
public abstract class ContentResponse extends Response {
    /**
     * Results from Content Search, one entry per content entity type that was searched.
     * Only set when Content Search is enabled and content results were requested.
     */
    public List<ContentSearchResultItem> contentSearchResults;

    /**
     * Gets the Content Search results for the given entity type.
     * @param entityType The content entity type, e.g. "Brand". Case-sensitive.
     * @return The results for that entity type, or null if that type was not searched.
     */
    public EntityCollection getContentSearchResults(String entityType) {
        if (contentSearchResults != null)
            for (ContentSearchResultItem item : contentSearchResults)
                if (entityType.equals(item.entityType))
                    return item.results;

        return null;
    }
}