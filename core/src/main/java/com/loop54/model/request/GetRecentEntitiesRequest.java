package com.loop54.model.request;

import java.util.List;

import com.loop54.model.request.parameters.EntityCollectionParameters;

/** This class used to perform a request to get entities that a user (or all users) most recently interacted with or navigated to. */
public class GetRecentEntitiesRequest extends Request {
    /**
     * @param  behaviorType interaction or navigation type to include (such as {@literal click}, {@literal purchase} or {@literal search}).
     * @param  forUserId    user ID (normally the same as the one in the User-Id header) to retrieve the most recent entities for that user or null to retrieve the globally most recent entities.
     * @param  entityType   entity types to include (such as {@literal Product} or {@literal Query})
     */
    public GetRecentEntitiesRequest(String behaviorType, String forUserId, List<String> entityType) {
        this.behaviorType = behaviorType;
        this.forUserId = forUserId;
        this.entityType = entityType;
    }

    /**
     * Retrieve the globally most recent products for a specfic user
     * @param  behaviorType interaction or navigation type to include (such as {@literal click}, {@literal purchase} or {@literal search}).
     * @param  forUserId    user ID (normally the same as the one in the User-Id header) to retrieve the most recent entities for that user
     */
    public GetRecentEntitiesRequest(String behaviorType, String forUserId) { this(behaviorType, forUserId, null); }

    /**
     * Retrieve the globally most recent products
     * @param  behaviorType interaction or navigation type to include (such as {@literal click}, {@literal purchase} or {@literal search}).
     */
    public GetRecentEntitiesRequest(String behaviorType) { this(behaviorType, null, null); }

    /**
     * The interaction or navigation type to include (such as {@literal click}, {@literal purchase} or {@literal search}).
     */
    public String behaviorType;

    /**
     * A user ID (normally the same as the one in the User-Id header) to retrieve the most recent entities for that user or null to retrieve the globally most recent entities.
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