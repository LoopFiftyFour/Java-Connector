package com.loop54.exceptions;

import com.loop54.model.response.ErrorDetails;

/** Thrown when the engine is reachable, but the response status code was not 200. */
public class EngineStatusCodeException extends Loop54Exception {
    /** Details about the error provided by the engine. */
    public final ErrorDetails details;

    public EngineStatusCodeException(ErrorDetails details) {
        super(details.title);
        this.details = details;
    }
}
