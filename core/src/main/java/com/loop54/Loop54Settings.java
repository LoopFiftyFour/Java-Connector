package com.loop54;

/** Global settings for the Loop54 e-commerce search engine setup. */
public class Loop54Settings {
    private final String endpoint;

    private final String apiKey;

    private final boolean requireHttps;

    private final int requestTimeoutMs;

    /**
     * @param endpoint The endpoint of the Loop54 search engine. If you don't have this please contact customer support.
     * @param apiKey The api key authenticating you as a trusted caller. If you don't have this please contact customer support.
     * @param requireHttps Whether to enforce the use of HTTPS. If this is true the endpoint must use the HTTPS protocol.
     * @param requestTimeoutMs How long to wait, in milliseconds, for the Loop54 search engine before failing.
     */
    public Loop54Settings(String endpoint, String apiKey, boolean requireHttps, int requestTimeoutMs) {
        if (endpoint == null)
            throw new IllegalArgumentException("Endpoint can not be null");

        this.endpoint = endpoint;
        this.apiKey = apiKey;
        this.requireHttps = requireHttps;
        this.requestTimeoutMs = requestTimeoutMs;
    }

    /**
     * Forwards to {@link #Loop54Settings(String, String, boolean, int)}, with HTTPS enabled, and a request
     * timeout of 10 seconds.
     */
    public Loop54Settings(String endpoint, String apiKey) {
        this(endpoint, apiKey, true, 10000);
    }

    /** @return The endpoint of the Loop54 search engine. */
    public String getEndpoint() { return endpoint; }

    /** @return The api key authenticating you as a trusted caller. */
    public String getApiKey() { return apiKey; }

    /** @return Whether to enforce the use of HTTPS. If this is true the endpoint must use the HTTPS protocol. */
    public boolean getRequireHttps() { return requireHttps; }

    /** @return How long to wait, in milliseconds, for the Loop54 search engine before failing. */
    public int getRequestTimeoutMs() { return requestTimeoutMs; }
}
