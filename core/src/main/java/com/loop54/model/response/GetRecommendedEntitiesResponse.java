package com.loop54.model.response;

/** TThe result of a request to get personalized recommendations. */
public class GetRecommendedEntitiesResponse extends Response {
    /** The personalized recommended entities. */
    public EntityCollection results;
}
