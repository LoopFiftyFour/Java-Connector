package com.loop54.http;

import java.util.concurrent.ExecutionException;

import org.junit.jupiter.api.Test;

import com.loop54.Loop54Settings;
import com.loop54.exceptions.EngineNotReachableException;
import com.loop54.exceptions.EngineStatusCodeException;
import com.loop54.model.request.Request;
import com.loop54.model.response.Response;
import com.loop54.user.UserMetaData;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RequestManagerTest {
    private ResourceHoster setupHoster(final String data, final int statusCode) {
        final var hoster = new ResourceHoster();
        hoster.resourceString = data;
        hoster.statusCode = statusCode;
        hoster.start();

        return hoster;
    }

    @Test
    void successfulRequest() throws Exception {
        final var hoster = setupHoster("{\"customData\": {}}", 200);

        final var settings = new Loop54Settings("http://localhost:" + hoster.port, null, false, 1000);
        try (final IRequestManager manager = new RequestManager(settings)) {
            final var metaData = new UserMetaData("User.Name");
            metaData.referer = "https://www.loop54.com";
            metaData.userAgent = "LoopBrowser";
            metaData.ipAddress = "127.0.0.1";

            final var response = manager.callEngineAsync("whatever", new Request(), metaData, Response.class).get();

            hoster.stop();

            assertEquals("/whatever", hoster.calledPath);
            assertEquals("User.Name", hoster.calledHeaders.get(Headers.USER_ID).get(0));
            assertEquals("https://www.loop54.com", hoster.calledHeaders.get(Headers.REFERER).get(0));
            assertEquals("LoopBrowser", hoster.calledHeaders.get(Headers.USER_AGENT).get(0));
            assertEquals("127.0.0.1", hoster.calledHeaders.get(Headers.IP_ADDRESS).get(0));
            assertNotNull(response.customData);
            assertTrue(response.customData.isEmpty());
        }
    }

    @Test
    void statusCodeError() throws Exception {
        final var hoster = setupHoster("{\"error\": { \"code\": 400, \"status\": \"Bad request\", " +
                "\"title\": \"The request was not valid.\", \"detail\": \"Field skip is not within the allowed range.\", " +
                "\"parameter\": \"request.results.skip\"}}", 400);

        final var settings = new Loop54Settings("http://localhost:" + hoster.port, null, false, 1000);
        try (final IRequestManager manager = new RequestManager(settings)) {
            final var metaData = new UserMetaData("User.Name");

            final var exception = assertThrows(ExecutionException.class,
                    () -> manager.callEngineAsync("whatever", new Request(), metaData, Response.class).get());
            final var realException = assertInstanceOf(EngineStatusCodeException.class, exception.getCause());

            hoster.stop();

            assertEquals("/whatever", hoster.calledPath);
            assertEquals("User.Name", hoster.calledHeaders.get(Headers.USER_ID).get(0));
            assertEquals(400, realException.details.code);
            assertEquals("Bad request", realException.details.status);
            assertEquals("The request was not valid.", realException.details.title);
            assertEquals("The request was not valid.", realException.getMessage());
            assertEquals("request.results.skip", realException.details.parameter);
            assertEquals("Field skip is not within the allowed range.", realException.details.detail);
        }
    }

    @Test
    void unreachableError() throws Exception {
        final var settings = new Loop54Settings("http://localhost:1337", null, false, 100);
        try (final IRequestManager manager = new RequestManager(settings)) {
            final var metaData = new UserMetaData("User.Name");

            final var exception = assertThrows(ExecutionException.class,
                    () -> manager.callEngineAsync("whatever", new Request(), metaData, Response.class).get());
            assertSame(EngineNotReachableException.class, exception.getCause().getClass());
        }
    }
}
