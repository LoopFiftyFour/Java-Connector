package com.loop54;

import com.loop54.exceptions.Loop54Exception;
import com.loop54.model.request.*;
import com.loop54.model.response.*;

import java.util.concurrent.CompletableFuture;

public interface ILoop54Client {
    AutoCompleteResponse autoComplete(AutoCompleteRequest request) throws Loop54Exception;
    AutoCompleteResponse autoComplete(RequestContainer<AutoCompleteRequest> request) throws Loop54Exception;
    CompletableFuture<AutoCompleteResponse> autoCompleteAsync(AutoCompleteRequest request);
    CompletableFuture<AutoCompleteResponse> autoCompleteAsync(RequestContainer<AutoCompleteRequest> request);

    SearchResponse search(SearchRequest request) throws Loop54Exception;
    SearchResponse search(RequestContainer<SearchRequest> request) throws Loop54Exception;
    CompletableFuture<SearchResponse> searchAsync(SearchRequest request);
    CompletableFuture<SearchResponse> searchAsync(RequestContainer<SearchRequest> request);

    GetEntitiesResponse getEntities(GetEntitiesRequest request) throws Loop54Exception;
    GetEntitiesResponse getEntities(RequestContainer<GetEntitiesRequest> request) throws Loop54Exception;
    CompletableFuture<GetEntitiesResponse> getEntitiesAsync(GetEntitiesRequest request);
    CompletableFuture<GetEntitiesResponse> getEntitiesAsync(RequestContainer<GetEntitiesRequest> request);

    GetEntitiesByAttributeResponse getEntitiesByAttribute(GetEntitiesByAttributeRequest request) throws Loop54Exception;
    GetEntitiesByAttributeResponse getEntitiesByAttribute(RequestContainer<GetEntitiesByAttributeRequest> request) throws Loop54Exception;
    CompletableFuture<GetEntitiesByAttributeResponse> getEntitiesByAttributeAsync(GetEntitiesByAttributeRequest request);
    CompletableFuture<GetEntitiesByAttributeResponse> getEntitiesByAttributeAsync(RequestContainer<GetEntitiesByAttributeRequest> request);

    GetRelatedEntitiesResponse getRelatedEntities(GetRelatedEntitiesRequest request) throws Loop54Exception;
    GetRelatedEntitiesResponse getRelatedEntities(RequestContainer<GetRelatedEntitiesRequest> request) throws Loop54Exception;
    CompletableFuture<GetRelatedEntitiesResponse> getRelatedEntitiesAsync(GetRelatedEntitiesRequest request);
    CompletableFuture<GetRelatedEntitiesResponse> getRelatedEntitiesAsync(RequestContainer<GetRelatedEntitiesRequest> request);

    GetComplementaryEntitiesResponse getComplementaryEntities(GetComplementaryEntitiesRequest request) throws Loop54Exception;
    GetComplementaryEntitiesResponse getComplementaryEntities(RequestContainer<GetComplementaryEntitiesRequest> request) throws Loop54Exception;
    CompletableFuture<GetComplementaryEntitiesResponse> getComplementaryEntitiesAsync(GetComplementaryEntitiesRequest request);
    CompletableFuture<GetComplementaryEntitiesResponse> getComplementaryEntitiesAsync(RequestContainer<GetComplementaryEntitiesRequest> request);

    GetBasketRecommendationsResponse getBasketRecommendations(GetBasketRecommendationsRequest request) throws Loop54Exception;
    GetBasketRecommendationsResponse getBasketRecommendations(RequestContainer<GetBasketRecommendationsRequest> request) throws Loop54Exception;
    CompletableFuture<GetBasketRecommendationsResponse> getBasketRecommendationsAsync(GetBasketRecommendationsRequest request);
    CompletableFuture<GetBasketRecommendationsResponse> getBasketRecommendationsAsync(RequestContainer<GetBasketRecommendationsRequest> request);

    GetRecommendedEntitiesResponse getRecommendedEntities(GetRecommendedEntitiesRequest request) throws Loop54Exception;
    GetRecommendedEntitiesResponse getRecommendedEntities(RequestContainer<GetRecommendedEntitiesRequest> request) throws Loop54Exception;
    CompletableFuture<GetRecommendedEntitiesResponse> getRecommendedEntitiesAsync(GetRecommendedEntitiesRequest request);
    CompletableFuture<GetRecommendedEntitiesResponse> getRecommendedEntitiesAsync(RequestContainer<GetRecommendedEntitiesRequest> request);

    GetPopularEntitiesResponse getPopularEntities(GetPopularEntitiesRequest request) throws Loop54Exception;
    GetPopularEntitiesResponse getPopularEntities(RequestContainer<GetPopularEntitiesRequest> request) throws Loop54Exception;
    CompletableFuture<GetPopularEntitiesResponse> getPopularEntitiesAsync(GetPopularEntitiesRequest request);
    CompletableFuture<GetPopularEntitiesResponse> getPopularEntitiesAsync(RequestContainer<GetPopularEntitiesRequest> request);

    GetRecentEntitiesResponse getRecentEntities(GetRecentEntitiesRequest request) throws Loop54Exception;
    GetRecentEntitiesResponse getRecentEntities(RequestContainer<GetRecentEntitiesRequest> request) throws Loop54Exception;
    CompletableFuture<GetRecentEntitiesResponse> getRecentEntitiesAsync(GetRecentEntitiesRequest request);
    CompletableFuture<GetRecentEntitiesResponse> getRecentEntitiesAsync(RequestContainer<GetRecentEntitiesRequest> request);

    Response sync() throws Loop54Exception;
    Response sync(Request request) throws Loop54Exception;
    Response sync(RequestContainer<Request> request) throws Loop54Exception;
    CompletableFuture<Response> syncAsync();
    CompletableFuture<Response> syncAsync(Request request);
    CompletableFuture<Response> syncAsync(RequestContainer<Request> request);
	
    Response createEvents(CreateEventsRequest request) throws Loop54Exception;
    Response createEvents(RequestContainer<CreateEventsRequest> request) throws Loop54Exception;
    CompletableFuture<Response> createEventsAsync(CreateEventsRequest request);
    CompletableFuture<Response> createEventsAsync(RequestContainer<CreateEventsRequest> request);

    Response customCall(String name, Request request) throws Loop54Exception;
    Response customCall(String name, RequestContainer<Request> request) throws Loop54Exception;
    CompletableFuture<Response> customCallAsync(String name, Request request);
    CompletableFuture<Response> customCallAsync(String name, RequestContainer<Request> request);
}
