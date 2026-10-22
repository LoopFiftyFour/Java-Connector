package com.loop54.model.request;

import com.loop54.model.request.parameters.EntityCollectionParameters;

import java.util.List;
import java.util.function.Consumer;

/** This class used to perform a request to get entities that a user (or all users) most commonly interacted with or navigated to. */
public class GetPopularEntitiesRequest extends GetPopularOrRecentEntitiesRequest {

    /**
     * @param  behaviorType interaction or navigation type to include (such as {@literal click}, {@literal purchase} or {@literal search}).
     * @param  forUserId    a user ID to retrieve the most common/recent entities for that user or null to retrieve the globally most common/recent entities.
     * {@link GetPopularOrRecentEntitiesRequest#CURRENT_USER_PLACEHOLDER} can be specified to use the user ID from the User-Id request header.
     * @param  entityType   entity types to include (such as {@literal Product} or {@literal Query})
     */
    public GetPopularEntitiesRequest(String behaviorType, String forUserId, List<String> entityType) {
        super(behaviorType, forUserId, entityType);
    }

    /**
     * Retrieve the globally most common entities for a specfic user
     * @param  behaviorType interaction or navigation type to include (such as {@literal click}, {@literal purchase} or {@literal search}).
     * @param  forUserId    a user ID to retrieve the most common/recent entities for that user or null to retrieve the globally most common/recent entities.
     * {@link GetPopularOrRecentEntitiesRequest#CURRENT_USER_PLACEHOLDER} can be specified to use the user ID from the User-Id request header.
    */
    public GetPopularEntitiesRequest(String behaviorType, String forUserId) { super(behaviorType, forUserId, null); }

    /**
     * Retrieve the globally most common products
     * @param  behaviorType interaction or navigation type to include (such as {@literal click}, {@literal purchase} or {@literal search}).
     */
    public GetPopularEntitiesRequest(String behaviorType) { super(behaviorType, null, null); }

    /**
     * Creates a request to get the most popular entities for the current user.
     *
     * @param behaviorType interaction or navigation type to include (such as {@literal click}, {@literal purchase}, or {@literal search}).
     * @param entityType   entity types to include (such as {@literal Product} or {@literal Query}).
     * @return a new instance of {@code GetPopularEntitiesRequest} for the current user.
     */
    public static GetPopularEntitiesRequest forCurrentUser(String behaviorType, List<String> entityType) {
        return new GetPopularEntitiesRequest(behaviorType, CURRENT_USER_PLACEHOLDER, entityType);
    }

    /**
     * Configures {@link #resultsOptions}, e.g. {@code request.withResults(r -> r.page(0, 20).distinctFacet("Category"))}.
     * @param configurator Applied to {@link #resultsOptions}.
     * @return This request, for chaining.
     */
    public GetPopularEntitiesRequest withResults(final Consumer<EntityCollectionParameters> configurator) {
        configurator.accept(resultsOptions);
        return this;
    }

    @Override
    public GetPopularEntitiesRequest withCustomData(final String key, final Object value) {
        super.withCustomData(key, value);
        return this;
    }
}
