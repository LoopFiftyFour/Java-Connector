package com.loop54.model.request;

import java.util.HashMap;
import java.util.Map;

/** The base request class used for issuing custom requests to the Loop54 e-commerce search engine. */
public class Request {
    /** Any additional, non-standard, data. Contact support for information about how and when to use this. */
    public Map<String, Object> customData;

    /**
     * Adds the object value using the provided key to the custom data of the request.
     * @param key Key to set the data on. The keys will be treated as case-sensitive.
     * @param value Value to add to the custom data.
     */
    public void addCustomData(String key, Object value) {
        if (customData == null)
            customData = new HashMap<>(); //Case sensitive

        customData.put(key, value);
    }
}
