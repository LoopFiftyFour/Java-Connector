package com.loop54.test;

import com.loop54.ILoop54Client;
import com.loop54.RequestContainer;
import com.loop54.exceptions.Loop54ArgumentException;
import com.loop54.exceptions.Loop54Exception;
import com.loop54.http.IRequestManager;
import com.loop54.model.request.AutoCompleteRequest;
import com.loop54.model.request.Request;
import com.loop54.model.request.SearchRequest;
import com.loop54.model.response.Response;
import com.loop54.user.IRemoteClientInfo;
import com.loop54.user.UserMetaData;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;

public class Loop54Client {
    static class TestRequestManager implements IRequestManager {
        public String calledAction;
        public Object calledRequestData;
        public UserMetaData calledMetaData;

        @Override
        public <T extends Response> CompletableFuture<T> callEngineAsync(String action, Request requestData, UserMetaData metaData, Class<T> responseType) {
            calledAction = action;
            calledRequestData = requestData;
            calledMetaData = metaData;
            return CompletableFuture.completedFuture(null);
        }
    }

    @Test
    public void searchWithoutOverrides() throws Loop54Exception {
        TestRequestManager requestManager = new TestRequestManager();
        com.loop54.Loop54Client client = new com.loop54.Loop54Client(requestManager, () -> new NullClientInfo());

        SearchRequest search = new SearchRequest("pork belly");
        client.search(search);

        assertEquals("search", requestManager.calledAction);
        assertSame(search, requestManager.calledRequestData);
        assertNotNull(requestManager.calledMetaData);
        assertNotNull(requestManager.calledMetaData.userId);
    }

    @Test
    public void searchWithoutOverridesAndWithFaultyClientInfoProvider() {
        TestRequestManager requestManager = new TestRequestManager();
        com.loop54.Loop54Client client = new com.loop54.Loop54Client(requestManager, () -> null);

        SearchRequest search = new SearchRequest("pork belly");

        // Should throw error to tell us to implement the IRemoteClientInfoProvider correctly
        Exception exception = assertThrows(Loop54ArgumentException.class, () -> client.search(search));
        assertTrue(exception.getMessage().contains("returned a null " + IRemoteClientInfo.class.getSimpleName()), exception.getMessage());
    }

    @Test
    public void searchWithMetaDataOverrides() throws Loop54Exception {
        TestRequestManager requestManager = new TestRequestManager();
        com.loop54.Loop54Client client = new com.loop54.Loop54Client(requestManager, () -> new NullClientInfo());

        SearchRequest search = new SearchRequest("pork belly");
        client.search(com.loop54.Loop54Client.getRequestContainer(search, new UserMetaData("test.user")));

        assertEquals("search", requestManager.calledAction);
        assertSame(search, requestManager.calledRequestData);
        assertNotNull(requestManager.calledMetaData);
        assertEquals("test.user", requestManager.calledMetaData.userId);
    }

    @Test
    public void searchWithMetaDataAndClientInfoOverrides() {
        callWithOverrides((c, r) -> c.search(r), new SearchRequest("pork belly"), "search");
    }

    @Test
    public void autoCompleteWithMetaDataAndClientInfoOverrides() {
        callWithOverrides((c, r) -> c.autoComplete(r), new AutoCompleteRequest("pork bel"), "autoComplete");
    }

    private <T extends Request> void callWithOverrides(ThrowingBiConsumer<ILoop54Client, RequestContainer<T>> call, T request, String expectedAction) {
        String remoteIp = "127.0.0.1";
        String referer = "referer.se";
        String userAgent = "testcase";

        TestRequestManager requestManager = new TestRequestManager();
        NullClientInfo clientInfo = new NullClientInfo() {
            @Override public String getRemoteIp() { return remoteIp; }
            @Override public String getReferrer() { return referer; }
            @Override public String getUserAgent() { return userAgent; }
        };

        com.loop54.Loop54Client client = new com.loop54.Loop54Client(requestManager, () -> clientInfo);

        call.accept(client, com.loop54.Loop54Client.getRequestContainer(request, new UserMetaData("test.user")));

        assertEquals(expectedAction, requestManager.calledAction);
        assertSame(request, requestManager.calledRequestData);
        assertNotNull(requestManager.calledMetaData);
        assertEquals("test.user", requestManager.calledMetaData.userId);
        assertEquals(remoteIp, requestManager.calledMetaData.ipAddress);
        assertEquals(referer, requestManager.calledMetaData.referer);
        assertEquals(userAgent, requestManager.calledMetaData.userAgent);
    }
}

