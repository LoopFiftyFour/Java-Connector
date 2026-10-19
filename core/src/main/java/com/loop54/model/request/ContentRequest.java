package com.loop54.model.request;

import com.loop54.model.request.parameters.TypedEntityCollectionParameters;

import java.util.ArrayList;
import java.util.List;

/** Base class for requests that can also retrieve Content Search results. */
public abstract class ContentRequest extends Request {
    /**
     * Parameters for specifying which content results to retrieve (Content Search). One entry per content entity type,
     * e.g. "Brand" or "Article", each with its own paging, sorting, faceting and filtering. If not set, no content is searched.
     * Does not affect the product results.
     */
    public List<TypedEntityCollectionParameters> contentResultsOptions;

    /**
     * Adds a content entity type to search and returns its parameters, so that paging, sorting, faceting and filtering can be set on it.
     * @param entityType The content entity type to search, e.g. "Brand". Case-sensitive.
     * @return The parameters for the added content entity type.
     */
    public TypedEntityCollectionParameters addContentResultsOptions(String entityType) {
        if (contentResultsOptions == null)
            contentResultsOptions = new ArrayList<>();

        TypedEntityCollectionParameters options = new TypedEntityCollectionParameters(entityType);
        contentResultsOptions.add(options);
        return options;
    }
}