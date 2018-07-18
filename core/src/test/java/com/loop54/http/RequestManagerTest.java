package com.loop54.http;

import com.loop54.Loop54Settings;
import com.loop54.exceptions.EngineNotReachableException;
import com.loop54.exceptions.EngineStatusCodeException;
import com.loop54.model.request.Request;
import com.loop54.model.response.Response;
import com.loop54.user.UserMetaData;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;

public class RequestManagerTest {
    private ResourceHoster setupHoster(String data, int statusCode) {
        ResourceHoster hoster = new ResourceHoster();
        hoster.resourceString = data;
        hoster.statusCode = statusCode;
        hoster.start();

        return hoster;
    }

    @Test
    public void successfulRequest() throws Exception {
        ResourceHoster hoster = setupHoster("{\"customData\": {}}", 200);

        Loop54Settings settings = new Loop54Settings("http://localhost:" + hoster.port, null, false, 1000);
        com.loop54.http.RequestManager manager = new com.loop54.http.RequestManager(settings);

        UserMetaData metaData = new UserMetaData("User.Name");
        metaData.referer = "https://www.loop54.com";
        metaData.userAgent = "LoopBrowser";
        metaData.ipAddress = "127.0.0.1";

        Response response = manager.callEngineAsync("whatever", new Request(), metaData, Response.class).get();

        hoster.stop();

        assertEquals("/whatever", hoster.calledPath);
        assertEquals("User.Name", hoster.calledHeaders.get(Headers.USER_ID).get(0));
        assertEquals("https://www.loop54.com", hoster.calledHeaders.get(Headers.REFERER).get(0));
        assertEquals("LoopBrowser", hoster.calledHeaders.get(Headers.USER_AGENT).get(0));
        assertEquals("127.0.0.1", hoster.calledHeaders.get(Headers.IP_ADDRESS).get(0));
        assertNotNull(response.customData);
        assertTrue(response.customData.isEmpty());
    }

    @Test
    public void statusCodeError() {
        ResourceHoster hoster = setupHoster("{\"error\": { \"code\": 400, \"status\": \"Bad request\", " +
                "\"title\": \"The request was not valid.\", \"detail\": \"Field skip is not within the allowed range.\", " +
                "\"parameter\": \"request.results.skip\"}}", 400);

        Loop54Settings settings = new Loop54Settings("http://localhost:" + hoster.port, null, false, 1000);
        com.loop54.http.RequestManager manager = new com.loop54.http.RequestManager(settings);

        UserMetaData metaData = new UserMetaData("User.Name");

        Exception exception = assertThrows(ExecutionException.class, () -> manager.callEngineAsync("whatever", new Request(), metaData, Response.class).get());
        EngineStatusCodeException realException = (EngineStatusCodeException)exception.getCause();

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

    @Test
    public void unreachableError() {
        Loop54Settings settings = new Loop54Settings("http://localhost:1337", null, false, 100);
        com.loop54.http.RequestManager manager = new com.loop54.http.RequestManager(settings);

        UserMetaData metaData = new UserMetaData("User.Name");

        Exception exception = assertThrows(ExecutionException.class, () -> manager.callEngineAsync("whatever", new Request(), metaData, Response.class).get());
        assertEquals(EngineNotReachableException.class, exception.getCause().getClass());
    }
}
