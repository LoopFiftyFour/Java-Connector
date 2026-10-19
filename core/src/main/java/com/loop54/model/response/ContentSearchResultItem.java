package com.loop54.model.response;

/** The Content Search results for one content entity type. */
public class ContentSearchResultItem {
    /** The content entity type these results are for, e.g. "Brand". */
    public String entityType;

    /** The results for this entity type, in the same format as the product results. */
    public EntityCollection results;
}