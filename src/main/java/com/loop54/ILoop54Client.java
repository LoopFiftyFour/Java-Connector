package com.loop54;

import com.loop54.exceptions.Loop54Exception;
import com.loop54.model.request.AutoCompleteRequest;
import com.loop54.model.request.SearchRequest;
import com.loop54.model.response.AutoCompleteResponse;
import com.loop54.model.response.SearchResponse;

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

}
