package com.loop54;

import com.loop54.exceptions.ClientInfoException;
import com.loop54.exceptions.Loop54ArgumentException;
import com.loop54.exceptions.Loop54Exception;
import com.loop54.http.IRequestManager;
import com.loop54.model.request.*;
import com.loop54.model.response.*;
import com.loop54.user.IRemoteClientInfo;
import com.loop54.user.IRemoteClientInfoProvider;
import com.loop54.user.UserMetaData;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * Default implementation of the ILoop54Client interface.
 * <p>
 * When issuing a request the user data will be used in this order of priority:<br>
 * 1. The {@link UserMetaData} provided in {@link RequestContainer} in the method call.<br>
 * 2. The {@link IRemoteClientInfo} gotten from the {@link IRemoteClientInfoProvider}
 */
public class Loop54Client implements ILoop54Client {
    private static final String SEARCH_REQUEST_NAME = "search";
    private static final String AUTO_COMPLETE_REQUEST_NAME = "autoComplete";
    private static final String GET_ENTITIES_REQUEST_NAME = "getEntities";
    private static final String GET_ENTITIES_BY_ATTRIBUTE_REQUEST_NAME = "getEntitiesByAttribute";
    private static final String GET_RELATED_ENTITIES_REQUEST_NAME = "getRelatedEntities";
    private static final String GET_COMPLEMENTARY_ENTITIES_REQUEST_NAME = "getComplementaryEntities";
    private static final String CREATE_EVENTS_REQUEST_NAME = "createEvents";
    private static final String SYNC_REQUEST_NAME = "sync";

    private final IRequestManager requestManager;
    private final IRemoteClientInfoProvider remoteClientInfoProvider;

    /**
     * @param requestManager The {@link IRequestManager} to use when making calls to the api. Must not be null.
     * @param remoteClientInfoProvider The {@link IRemoteClientInfoProvider}.
     */
    public Loop54Client(IRequestManager requestManager, IRemoteClientInfoProvider remoteClientInfoProvider) {
        if (requestManager == null)
            throw new IllegalArgumentException("Request manager was null");
        if (remoteClientInfoProvider == null)
            throw new IllegalArgumentException("Remote client info provider was null");

        this.requestManager = requestManager;
        this.remoteClientInfoProvider = remoteClientInfoProvider;
    }

    /**
     * @return IRequestManager used by this client.
     */
    public IRequestManager getRequestManager() {
        return requestManager;
    }

    @Override public SearchResponse search(SearchRequest request) throws Loop54Exception { return getFuture(searchAsync(request)); }
    @Override public SearchResponse search(RequestContainer<SearchRequest> request) throws Loop54Exception { return getFuture(searchAsync(request)); }
    @Override public CompletableFuture<SearchResponse> searchAsync(SearchRequest request) { return searchAsync(getRequestContainer(request)); }
    @Override public CompletableFuture<SearchResponse> searchAsync(RequestContainer<SearchRequest> request) { return callEngine(SEARCH_REQUEST_NAME, request, SearchResponse.class); }

    @Override public GetEntitiesResponse getEntities(GetEntitiesRequest request) throws Loop54Exception { return getFuture(getEntitiesAsync(request)); }
    @Override public GetEntitiesResponse getEntities(RequestContainer<GetEntitiesRequest> request) throws Loop54Exception { return getFuture(getEntitiesAsync(request)); }
    @Override public CompletableFuture<GetEntitiesResponse> getEntitiesAsync(GetEntitiesRequest request) { return getEntitiesAsync(getRequestContainer(request)); }
    @Override public CompletableFuture<GetEntitiesResponse> getEntitiesAsync(RequestContainer<GetEntitiesRequest> request) { return callEngine(GET_ENTITIES_REQUEST_NAME, request, GetEntitiesResponse.class); }

    @Override public GetEntitiesByAttributeResponse getEntitiesByAttribute(GetEntitiesByAttributeRequest request) throws Loop54Exception { return getFuture(getEntitiesByAttributeAsync(request)); }
    @Override public GetEntitiesByAttributeResponse getEntitiesByAttribute(RequestContainer<GetEntitiesByAttributeRequest> request) throws Loop54Exception { return getFuture(getEntitiesByAttributeAsync(request)); }
    @Override public CompletableFuture<GetEntitiesByAttributeResponse> getEntitiesByAttributeAsync(GetEntitiesByAttributeRequest request) { return getEntitiesByAttributeAsync(getRequestContainer(request)); }
    @Override public CompletableFuture<GetEntitiesByAttributeResponse> getEntitiesByAttributeAsync(RequestContainer<GetEntitiesByAttributeRequest> request) { return callEngine(GET_ENTITIES_BY_ATTRIBUTE_REQUEST_NAME, request, GetEntitiesByAttributeResponse.class); }

    @Override public AutoCompleteResponse autoComplete(AutoCompleteRequest request) throws Loop54Exception { return getFuture(autoCompleteAsync(request)); }
    @Override public AutoCompleteResponse autoComplete(RequestContainer<AutoCompleteRequest> request) throws Loop54Exception { return getFuture(autoCompleteAsync(request)); }
    @Override public CompletableFuture<AutoCompleteResponse> autoCompleteAsync(AutoCompleteRequest request) { return autoCompleteAsync(getRequestContainer(request)); }
    @Override public CompletableFuture<AutoCompleteResponse> autoCompleteAsync(RequestContainer<AutoCompleteRequest> request) { return callEngine(AUTO_COMPLETE_REQUEST_NAME, request, AutoCompleteResponse.class); }

    @Override public GetRelatedEntitiesResponse getRelatedEntities(GetRelatedEntitiesRequest request) throws Loop54Exception { return getFuture(getRelatedEntitiesAsync(request)); }
    @Override public GetRelatedEntitiesResponse getRelatedEntities(RequestContainer<GetRelatedEntitiesRequest> request) throws Loop54Exception { return getFuture(getRelatedEntitiesAsync(request)); }
    @Override public CompletableFuture<GetRelatedEntitiesResponse> getRelatedEntitiesAsync(GetRelatedEntitiesRequest request) { return getRelatedEntitiesAsync(getRequestContainer(request)); }
    @Override public CompletableFuture<GetRelatedEntitiesResponse> getRelatedEntitiesAsync(RequestContainer<GetRelatedEntitiesRequest> request) { return callEngine(GET_RELATED_ENTITIES_REQUEST_NAME, request, GetRelatedEntitiesResponse.class); }

    @Override public GetComplementaryEntitiesResponse getComplementaryEntities(GetComplementaryEntitiesRequest request) throws Loop54Exception { return getFuture(getComplementaryEntitiesAsync(request)); }
    @Override public GetComplementaryEntitiesResponse getComplementaryEntities(RequestContainer<GetComplementaryEntitiesRequest> request) throws Loop54Exception { return getFuture(getComplementaryEntitiesAsync(request)); }
    @Override public CompletableFuture<GetComplementaryEntitiesResponse> getComplementaryEntitiesAsync(GetComplementaryEntitiesRequest request) { return getComplementaryEntitiesAsync(getRequestContainer(request)); }
    @Override public CompletableFuture<GetComplementaryEntitiesResponse> getComplementaryEntitiesAsync(RequestContainer<GetComplementaryEntitiesRequest> request) { return callEngine(GET_COMPLEMENTARY_ENTITIES_REQUEST_NAME, request, GetComplementaryEntitiesResponse.class); }

    @Override public Response sync() throws Loop54Exception { return sync(new Request()); }
    @Override public Response sync(Request request) throws Loop54Exception { return getFuture(syncAsync(request)); }
    @Override public Response sync(RequestContainer<Request> request) throws Loop54Exception { return getFuture(syncAsync(request)); }
    @Override public CompletableFuture<Response> syncAsync() { return syncAsync(new Request()); }
    @Override public CompletableFuture<Response> syncAsync(Request request) { return syncAsync(getRequestContainer(request)); }
    @Override public CompletableFuture<Response> syncAsync(RequestContainer<Request> request) { return callEngine(SYNC_REQUEST_NAME, request, Response.class); }
	
    @Override public Response createEvents(CreateEventsRequest request) throws Loop54Exception { return getFuture(createEventsAsync(request)); }
    @Override public Response createEvents(RequestContainer<CreateEventsRequest> request) throws Loop54Exception { return getFuture(createEventsAsync(request)); }
    @Override public CompletableFuture<Response> createEventsAsync(CreateEventsRequest request) { return createEventsAsync(getRequestContainer(request)); }
    @Override public CompletableFuture<Response> createEventsAsync(RequestContainer<CreateEventsRequest> request) { return callEngine(CREATE_EVENTS_REQUEST_NAME, request, Response.class); }

    /** Waits for the result of the given future, and extracts / wraps any exception into a {@link Loop54Exception}. */
    private <T extends Response> T getFuture(CompletableFuture<T> response) throws Loop54Exception {
        try {
            return response.get();
        } catch (ExecutionException ee) {
            if (ee.getCause() instanceof Loop54Exception)
                throw (Loop54Exception)ee.getCause();
            throw new Loop54Exception(ee);
        } catch (InterruptedException ie) {
            throw new Loop54Exception(ie);
        }
    }

    private <T extends Response> CompletableFuture<T> callEngine(String requestName, RequestContainer<? extends Request> request, Class<T> responseType) {
        UserMetaData metaData = prepareAndValidateRequest(requestName, request);

        return requestManager.callEngineAsync(requestName, request.request, metaData, responseType);
    }

    /**
     * Will wrap the request in a requestContainer to provide overriding options for the API call.
     *
     * @param requestData The request data to wrap in a container. For instance a {@link SearchRequest}.
     * @param metaDataOverrides Use these overrides to force certain values to take effect in the api call. For instance
     * if you set the {@link UserMetaData#userId} it will trump any data from a {@link IRemoteClientInfo}! This
     * could be useful if you want to use an internal customer id of a logged in user.
     * @param <T> The type of the request data to add overrides to.
     * @return A {@link RequestContainer} wrapping the {@link Request}.
     */
    public static <T extends Request> RequestContainer<T> getRequestContainer(T requestData, UserMetaData metaDataOverrides) {
        return new RequestContainer<>(requestData, metaDataOverrides);
    }

    public static <T extends Request> RequestContainer<T> getRequestContainer(T requestData) {
        return getRequestContainer(requestData, null);
    }

    private UserMetaData prepareAndValidateRequest(String requestName, RequestContainer<? extends Request> request) {
        if (requestName == null)
            throw new IllegalArgumentException("Request name was null");

        if (requestName.isEmpty() || Character.isUpperCase(requestName.charAt(0)))
            throw new IllegalArgumentException("The request name must be non-empty and lower camel case.");

        return getOrCreateMetaData(request);
    }

    private UserMetaData getOrCreateMetaData(RequestContainer<? extends Request> request) {
        UserMetaData metaData = request.metaDataOverrides.orElse(new UserMetaData());

        IRemoteClientInfo remoteClientInfo = remoteClientInfoProvider.getRemoteClientInfo();

        if (remoteClientInfo == null)
            throw new Loop54ArgumentException("The " + IRemoteClientInfoProvider.class.getSimpleName() + " returned a null " +
                    IRemoteClientInfo.class.getSimpleName() + ". Make sure you've implemented it correctly!");

        metaData.setFromClientInfo(remoteClientInfo);
        return metaData;
    }
}
