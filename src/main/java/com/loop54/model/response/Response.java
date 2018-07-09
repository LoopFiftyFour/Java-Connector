package com.loop54.model.response;

import java.util.Map;

/** A response from the engine. Used for responses that don't return any standardized data parameters. */
public class Response {
    /** Any additional, non-standard, data. Contact support for information about how and when to use this. */
    public Map<String, Object> customData;
}
