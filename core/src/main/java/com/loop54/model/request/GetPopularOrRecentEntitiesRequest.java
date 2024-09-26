package com.loop54.model.request;

import java.util.List;

import com.loop54.model.request.parameters.EntityCollectionParameters;

/// <summary>Base class for requests to get the most popular or most recent entities.</summary>
public abstract class GetPopularOrRecentEntitiesRequest extends Request
{
    public static final String CURRENT_USER_PLACEHOLDER = "(CurrentUser)";
    
    protected GetPopularOrRecentEntitiesRequest(String behaviorType, String forUserId, List<String> entityType) {
        this.behaviorType = behaviorType;
        this.entityType = entityType;
        this.forUserId = forUserId;
    }

    /**
     * The interaction or navigation type to include (such as {@literal click}, {@literal purchase} or {@literal search}).
     */
    public String behaviorType;

    /**
     * A user ID to retrieve the most common/recent entities for that user or null to retrieve the globally most common/recent entities.
     * {@link GetGetPopularOrRecentEntitiesRequest#CURRENT_USER_PLACEHOLDER} can be specified to use the user ID from the User-Id request header.
     */
    public String forUserId;
    
    /**
     * The entity types to include (such as {@literal Product} or {@literal Query}) or null for all.
     */
    public List<String> entityType;

    /**
     * Parameters for specifying which recommendations results to retrieve and how to format them.
     */
    public final EntityCollectionParameters resultsOptions = new EntityCollectionParameters();
}