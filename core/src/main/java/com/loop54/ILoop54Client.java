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

    Response createEvents(CreateEventsRequest request) throws Loop54Exception;
    Response createEvents(RequestContainer<CreateEventsRequest> request) throws Loop54Exception;
    CompletableFuture<Response> createEventsAsync(CreateEventsRequest request);
    CompletableFuture<Response> createEventsAsync(RequestContainer<CreateEventsRequest> request);
}
