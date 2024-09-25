package com.loop54.model.request;

import java.util.List;

import com.loop54.Loop54Client;
import com.loop54.model.request.parameters.EntityCollectionParameters;

/** This class used to perform a request to get entities that a user (or all users) most recently interacted with or navigated to. */
public class GetRecentEntitiesRequest extends Request {
    /**
     * @param  behaviorType interaction or navigation type to include (such as {@literal click}, {@literal purchase} or {@literal search}).
     * @param  forUserId    a user ID to retrieve the most common/recent entities for that user or null to retrieve the globally most common/recent entities.
     * The literal {@literal (CurrentUser)} can be specified to use the user ID from the User-Id request header.
     * @param  entityType   entity types to include (such as {@literal Product} or {@literal Query})
     */
    public GetRecentEntitiesRequest(String behaviorType, String forUserId, List<String> entityType) {
        this.behaviorType = behaviorType;
        this.forUserId = forUserId;
        this.entityType = entityType;
    }

    /**
     * Retrieve entities that a current user most recent interacted with or navigated to.
     * @param  behaviorType interaction or navigation type to include (such as {@literal click}, {@literal purchase} or {@literal search}).
     * @param  entityType   entity types to include (such as {@literal Product} or {@literal Query})
     */
    public GetRecentEntitiesRequest(String behaviorType, List<String> entityType) {
        this.behaviorType = behaviorType;
        this.forUserId = Loop54Client.CURRENT_USER_LITERAL;
        this.entityType = entityType;
    }

    /**
     * Retrieve the globally most recent products for a specfic user
     * @param  behaviorType interaction or navigation type to include (such as {@literal click}, {@literal purchase} or {@literal search}).
     * @param  forUserId    a user ID to retrieve the most common/recent entities for that user or null to retrieve the globally most common/recent entities.
     * The literal {@literal (CurrentUser)} can be specified to use the user ID from the User-Id request header.
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
     * A user ID to retrieve the most common/recent entities for that user or null to retrieve the globally most common/recent entities.
     * The literal {@literal (CurrentUser)} can be specified to use the user ID from the User-Id request header.
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