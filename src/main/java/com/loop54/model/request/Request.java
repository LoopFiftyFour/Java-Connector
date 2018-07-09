package com.loop54.model.request;

import java.util.Map;

/** The base request class used for issuing custom requests to the Loop54 e-commerce search engine. */
public class Request {
    /** Any additional, non-standard, data. Contact support for information about how and when to use this. */
    public Map<String, Object> customData;
}
