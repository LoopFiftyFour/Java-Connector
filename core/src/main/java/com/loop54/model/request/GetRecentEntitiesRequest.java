package com.loop54.model.request;

import java.util.List;

import com.loop54.Loop54Client;

/** This class used to perform a request to get entities that a user (or all users) most recently interacted with or navigated to. */
public class GetRecentEntitiesRequest extends GetPopularOrRecentEntitiesRequest {
    /**
     * @param  behaviorType interaction or navigation type to include (such as {@literal click}, {@literal purchase} or {@literal search}).
     * @param  forUserId    a user ID to retrieve the most common/recent entities for that user or null to retrieve the globally most common/recent entities.     
     * {@link Loop54Client#CURRENT_USER_PLACEHOLDER} can be specified to use the user ID from the User-Id request header.
     * @param  entityType   entity types to include (such as {@literal Product} or {@literal Query})
     */
    public GetRecentEntitiesRequest(String behaviorType, String forUserId, List<String> entityType) {
        super(behaviorType, forUserId, entityType);
    }

    /**
     * Retrieve the globally most recent products for a specfic user
     * @param  behaviorType interaction or navigation type to include (such as {@literal click}, {@literal purchase} or {@literal search}).
     * @param  forUserId    a user ID to retrieve the most common/recent entities for that user or null to retrieve the globally most common/recent entities.
     * {@link Loop54Client#CURRENT_USER_PLACEHOLDER} can be specified to use the user ID from the User-Id request header.
     */
    public GetRecentEntitiesRequest(String behaviorType, String forUserId) { super(behaviorType, forUserId, null); }

    /**
     * Retrieve the globally most recent products
     * @param  behaviorType interaction or navigation type to include (such as {@literal click}, {@literal purchase} or {@literal search}).
     */
    public GetRecentEntitiesRequest(String behaviorType) { super(behaviorType, null, null); }

    /**
     * Creates a request to get the most recent entities for the current user.
     *
     * @param behaviorType interaction or navigation type to include (such as {@literal click}, {@literal purchase}, or {@literal search}).
     * @param entityType   entity types to include (such as {@literal Product} or {@literal Query}).
     * @return a new instance of {@code GetRecentEntitiesRequest} for the current user.
     */
    public static GetRecentEntitiesRequest forCurrentUser(String behaviorType, List<String> entityType) {
        return new GetRecentEntitiesRequest(behaviorType, CURRENT_USER_PLACEHOLDER, entityType);
    }
}