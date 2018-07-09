package com.loop54.http;

import com.loop54.model.request.Request;
import com.loop54.model.response.Response;
import com.loop54.model.response.SearchResponse;
import com.loop54.user.UserMetaData;

import java.util.concurrent.CompletableFuture;

/** Handles the HTTP request to the Loop54 API. */
public interface IRequestManager {
    /**
     * Calls the loop54 search engine and returns a deserialized response object.
     *
     * @param action The type of request. This will translate into the resource on the actual HTTP request i.e. "endpoint/action". Must not be null.
     * @param requestData The query data to send to the engine. Must not be null.
     * @param metaData Data about the requesting user. Must not be null.
     * @param responseType The expected response type. E.g. if making a search call the expected type would be {@link SearchResponse}
     * @param <T> The expected response type. E.g. if making a search call the expected type would be {@link SearchResponse}
     * @return The deserialized response.
     */
    <T extends Response> CompletableFuture<T> callEngineAsync(String action, Request requestData, UserMetaData metaData, Class<T> responseType);
}
