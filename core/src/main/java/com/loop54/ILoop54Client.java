package com.loop54;

import java.io.Closeable;
import java.util.concurrent.CompletableFuture;

import com.loop54.exceptions.Loop54Exception;
import com.loop54.model.request.AutoCompleteRequest;
import com.loop54.model.request.CreateEventsRequest;
import com.loop54.model.request.GetBasketRecommendationsRequest;
import com.loop54.model.request.GetComplementaryEntitiesRequest;
import com.loop54.model.request.GetEntitiesByAttributeRequest;
import com.loop54.model.request.GetEntitiesRequest;
import com.loop54.model.request.GetPopularEntitiesRequest;
import com.loop54.model.request.GetRecentEntitiesRequest;
import com.loop54.model.request.GetRecommendedEntitiesRequest;
import com.loop54.model.request.GetRelatedEntitiesRequest;
import com.loop54.model.request.Request;
import com.loop54.model.request.SearchRequest;
import com.loop54.model.request.SuggestRequest;
import com.loop54.model.response.AutoCompleteResponse;
import com.loop54.model.response.GetBasketRecommendationsResponse;
import com.loop54.model.response.GetComplementaryEntitiesResponse;
import com.loop54.model.response.GetEntitiesByAttributeResponse;
import com.loop54.model.response.GetEntitiesResponse;
import com.loop54.model.response.GetPopularEntitiesResponse;
import com.loop54.model.response.GetRecentEntitiesResponse;
import com.loop54.model.response.GetRecommendedEntitiesResponse;
import com.loop54.model.response.GetRelatedEntitiesResponse;
import com.loop54.model.response.Response;
import com.loop54.model.response.SearchResponse;
import com.loop54.model.response.SuggestResponse;

public interface ILoop54Client extends Closeable {
    AutoCompleteResponse autoComplete(AutoCompleteRequest request) throws Loop54Exception;
    AutoCompleteResponse autoComplete(RequestContainer<AutoCompleteRequest> request) throws Loop54Exception;
    CompletableFuture<AutoCompleteResponse> autoCompleteAsync(AutoCompleteRequest request);
    CompletableFuture<AutoCompleteResponse> autoCompleteAsync(RequestContainer<AutoCompleteRequest> request);

    // The 'Suggest' endpoint methods have default implementations, so that existing custom implementations of this interface
    // (e.g. test doubles) keep compiling after upgrading. Loop54Client overrides all of them.
    default SuggestResponse suggest(final SuggestRequest request) throws Loop54Exception {
        throw new UnsupportedOperationException("Suggest is not supported by " + getClass().getName());
    }
    default SuggestResponse suggest(final RequestContainer<SuggestRequest> request) throws Loop54Exception {
        throw new UnsupportedOperationException("Suggest is not supported by " + getClass().getName());
    }
    default CompletableFuture<SuggestResponse> suggestAsync(final SuggestRequest request) {
        return CompletableFuture.failedFuture(new UnsupportedOperationException("Suggest is not supported by " + getClass().getName()));
    }
    default CompletableFuture<SuggestResponse> suggestAsync(final RequestContainer<SuggestRequest> request) {
        return CompletableFuture.failedFuture(new UnsupportedOperationException("Suggest is not supported by " + getClass().getName()));
    }

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
