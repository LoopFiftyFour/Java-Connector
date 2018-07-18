package com.loop54.exceptions;

/** Thrown when a connection to the engine cannot be made. Could have multiple causes. */
public class EngineNotReachableException extends Loop54Exception {
    public EngineNotReachableException(String message, Exception cause) {
        super(message, cause);
    }
}