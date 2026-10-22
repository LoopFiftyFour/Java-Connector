package com.loop54;

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
import com.loop54.user.NullClientInfo;

import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;

class Loop54ClientTest {
    static class TestRequestManager implements IRequestManager {
        String calledAction;
        Object calledRequestData;
        UserMetaData calledMetaData;

        @Override
        public <T extends Response> CompletableFuture<T> callEngineAsync(final String action, final Request requestData, final UserMetaData metaData,
                                                                         final Class<T> responseType) {
            calledAction = action;
            calledRequestData = requestData;
            calledMetaData = metaData;
            return CompletableFuture.completedFuture(null);
        }

        @Override
        public void close() {
            // No resources to close in test mock
        }
    }

    @Test
    void searchWithoutOverrides() throws Loop54Exception {
        final var requestManager = new TestRequestManager();
        final var client = new Loop54Client(requestManager, NullClientInfo::new);

        final var search = new SearchRequest("pork belly");
        client.search(search);

        assertEquals("search", requestManager.calledAction);
        assertSame(search, requestManager.calledRequestData);
        assertNotNull(requestManager.calledMetaData);
        assertNotNull(requestManager.calledMetaData.userId);
    }

    @Test
    void searchWithoutOverridesAndWithFaultyClientInfoProvider() {
        final var requestManager = new TestRequestManager();
        final var client = new Loop54Client(requestManager, () -> null);

        final var search = new SearchRequest("pork belly");

        // Should throw error to tell us to implement the IRemoteClientInfoProvider correctly
        final var exception = assertThrows(Loop54ArgumentException.class, () -> client.search(search));
        assertTrue(exception.getMessage().contains("returned a null " + IRemoteClientInfo.class.getSimpleName()), exception.getMessage());
    }

    @Test
    void searchWithMetaDataOverrides() throws Loop54Exception {
        final var requestManager = new TestRequestManager();
        final var client = new Loop54Client(requestManager, NullClientInfo::new);

        final var search = new SearchRequest("pork belly");
        client.search(Loop54Client.getRequestContainer(search, new UserMetaData("test.user")));

        assertEquals("search", requestManager.calledAction);
        assertSame(search, requestManager.calledRequestData);
        assertNotNull(requestManager.calledMetaData);
        assertEquals("test.user", requestManager.calledMetaData.userId);
    }

    @Test
    void searchWithMetaDataAndClientInfoOverrides() {
        callWithOverrides((c, r) -> c.search(r), new SearchRequest("pork belly"), "search");
    }

    @Test
    void autoCompleteWithMetaDataAndClientInfoOverrides() {
        callWithOverrides((c, r) -> c.autoComplete(r), new AutoCompleteRequest("pork bel"), "autoComplete");
    }

    private <T extends Request> void callWithOverrides(final ThrowingBiConsumer<ILoop54Client, RequestContainer<T>> call, final T request,
                                                       final String expectedAction) {
        final var remoteIp = "127.0.0.1";
        final var referer = "referer.se";
        final var userAgent = "testcase";

        final var requestManager = new TestRequestManager();
        final var clientInfo = new NullClientInfo() {
            @Override public String getRemoteIp() { return remoteIp; }
            @Override public String getReferrer() { return referer; }
            @Override public String getUserAgent() { return userAgent; }
        };

        final var client = new Loop54Client(requestManager, () -> clientInfo);

        call.accept(client, Loop54Client.getRequestContainer(request, new UserMetaData("test.user")));

        assertEquals(expectedAction, requestManager.calledAction);
        assertSame(request, requestManager.calledRequestData);
        assertNotNull(requestManager.calledMetaData);
        assertEquals("test.user", requestManager.calledMetaData.userId);
        assertEquals(remoteIp, requestManager.calledMetaData.ipAddress);
        assertEquals(referer, requestManager.calledMetaData.referer);
        assertEquals(userAgent, requestManager.calledMetaData.userAgent);
    }
}
