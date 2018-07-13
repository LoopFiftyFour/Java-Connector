package com.loop54.http;

import com.loop54.exceptions.EngineNotReachableException;
import com.loop54.Loop54Settings;
import com.loop54.Utils;
import com.loop54.VersionHeaders;
import com.loop54.exceptions.EngineStatusCodeException;
import com.loop54.exceptions.Loop54Exception;
import com.loop54.model.request.Request;
import com.loop54.model.response.ErrorResponse;
import com.loop54.model.response.Response;
import com.loop54.serialization.Serializer;
import com.loop54.user.UserMetaData;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

/** Handles the request to the Loop54 API. This can be used as a singleton. */
public class RequestManager implements IRequestManager {
    private final CloseableHttpClient httpClient;

    private static class RequestData {
        final String action;
        final String body;
        final UserMetaData userMetaData;
        final String apiKey;

        RequestData(String action, String body, UserMetaData userMetaData, String apiKey) {
            this.action = action;
            this.body = body;
            this.userMetaData = userMetaData;
            this.apiKey = apiKey;
        }
    }

    private final Loop54Settings settings;

    /**
     * @return Settings used by this request manager.
     */
    public Loop54Settings getSettings() {
        return settings;
    }

    /** @param settings The settings for this instance to use when calling Loop54. */
    public RequestManager(Loop54Settings settings) {
        if (settings == null)
            throw new IllegalArgumentException("Settings was null");

        this.settings = settings;

        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(settings.getRequestTimeoutMs())
                .setConnectionRequestTimeout(settings.getRequestTimeoutMs())
                .build();

        httpClient = HttpClients.custom().setDefaultRequestConfig(requestConfig).build();
    }

    @Override
    public <T extends Response> CompletableFuture<T> callEngineAsync(String action, Request requestData, UserMetaData metaData, Class<T> responseType) {
        if (action == null)
            throw new IllegalArgumentException("Action was null");

        if (requestData == null)
            throw new IllegalArgumentException("Request data was null");

        if (metaData == null)
            throw new IllegalArgumentException("Metadata was null");

        CompletableFuture<T> future = new CompletableFuture<>();

        CompletableFuture.runAsync(() -> {
            try {
                RequestData request = new RequestData(action, Serializer.serialize(requestData), metaData, settings.getApiKey());
                future.complete(makeHttpRequest(request, responseType));
            } catch (Loop54Exception e) {
                future.completeExceptionally(e);
            }
        });

        return future;
    }

    private <T extends Response> T makeHttpRequest(RequestData request, Class<T> responseType) throws Loop54Exception {
        String endpoint = getValidatedEndpoint();
        HttpPost httpPost = new HttpPost(endpoint + "/" + request.action);
        setHeadersOnRequest(httpPost, request);
        httpPost.setEntity(new StringEntity(request.body, "UTF-8"));

        CloseableHttpResponse response;

        try {
            response = httpClient.execute(httpPost);
        } catch (IOException ioe) {
            throw new EngineNotReachableException("Could not make request to engine at '" + endpoint + "', you might have entered the wrong endpoint or there might " +
                    "be a firewall blocking outgoing port 80", ioe);
        }

        try {
            String content;
            HttpEntity entity = response.getEntity();
            try {
                content = EntityUtils.toString(entity, "UTF-8");
            } catch (IOException ioe) {
                throw new EngineNotReachableException("Failed to receive a response from the engine at '" + endpoint + "'", ioe);
            }

            if (response.getStatusLine().getStatusCode() / 100 == 2)
                return Serializer.deserialize(content, responseType);
            else
                throw new EngineStatusCodeException(Serializer.deserialize(content, ErrorResponse.class).error);
        } finally {
            try {
                response.close();
            } catch (IOException ioe) {
                // http response could not be closed; ignore
            }
        }
    }

    private String getValidatedEndpoint() {
        String endpoint = Utils.fixEngineUrl(settings.getEndpoint());

        if (settings.getRequireHttps() && !Utils.urlIsHttps(endpoint))
            throw new IllegalArgumentException("If 'requireHttps' is true the endpoint needs to use the protocol 'https'");

        return endpoint;
    }

    private void setHeadersOnRequest(HttpPost httpPost, RequestData request) {
        httpPost.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
        httpPost.addHeader(VersionHeaders.API_VERSION_HEADER, VersionHeaders.API_VERSION);
        httpPost.addHeader(VersionHeaders.LIB_VERSION_HEADER, VersionHeaders.LIB_VERSION);
        addHeaderIfNotNull(httpPost, Headers.API_KEY, settings.getApiKey());
        addHeaderIfNotNull(httpPost, Headers.USER_ID, request.userMetaData.userId);
        addHeaderIfNotNull(httpPost, Headers.IP_ADDRESS, request.userMetaData.ipAddress);
        addHeaderIfNotNull(httpPost, Headers.USER_AGENT, request.userMetaData.userAgent);
        addHeaderIfNotNull(httpPost, Headers.REFERER, request.userMetaData.referer);
    }

    private void addHeaderIfNotNull(HttpPost httpPost, String headerName, String headerValue) {
        if (headerValue != null)
            httpPost.addHeader(headerName, headerValue);
    }
}
