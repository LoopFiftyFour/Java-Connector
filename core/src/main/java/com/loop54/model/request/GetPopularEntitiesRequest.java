package com.loop54.model.request;

import java.util.List;

import com.loop54.Loop54Client;
import com.loop54.model.request.parameters.EntityCollectionParameters;

/** This class used to perform a request to get entities that a user (or all users) most commonly interacted with or navigated to. */
public class GetPopularEntitiesRequest extends Request {

    /**
     * @param  behaviorType interaction or navigation type to include (such as {@literal click}, {@literal purchase} or {@literal search}).
     * @param  forUserId    a user ID to retrieve the most common/recent entities for that user or null to retrieve the globally most common/recent entities.
     * {@link Loop54Client#CURRENT_USER_PLACEHOLDER} can be specified to use the user ID from the User-Id request header.
     * @param  entityType   entity types to include (such as {@literal Product} or {@literal Query})
     */
    public GetPopularEntitiesRequest(String behaviorType, String forUserId, List<String> entityType) {
        this.behaviorType = behaviorType;
        this.forUserId = forUserId;
        this.entityType = entityType;
    }

    /**
     * Retrieve the globally most common products
     * @param  behaviorType interaction or navigation type to include (such as {@literal click}, {@literal purchase} or {@literal search}).
     */
    public GetPopularEntitiesRequest(String behaviorType) { this(behaviorType, null, null); }

    /**
     * Creates a request to get the most popular entities for the current user.
     *
     * @param behaviorType interaction or navigation type to include (such as {@literal click}, {@literal purchase}, or {@literal search}).
     * @param entityType   entity types to include (such as {@literal Product} or {@literal Query}).
     * @return a new instance of {@code GetPopularEntitiesRequest} for the current user.
     */
    public static GetPopularEntitiesRequest forCurrentUser(String behaviorType, List<String> entityType) {
        return new GetPopularEntitiesRequest(behaviorType, Loop54Client.CURRENT_USER_PLACEHOLDER, entityType);
    }

    /**
     * The interaction or navigation type to include (such as {@literal click}, {@literal purchase} or {@literal search}).
     */
    public String behaviorType;

    /**
     * A user ID to retrieve the most common/recent entities for that user or null to retrieve the globally most common/recent entities.
     * {@link Loop54Client#CURRENT_USER_PLACEHOLDER} can be specified to use the user ID from the User-Id request header.
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