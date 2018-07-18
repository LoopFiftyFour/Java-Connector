package com.loop54.exceptions;

/** Base class for all Loop54-specific checked exceptions. */
public class Loop54Exception extends Exception {
    public Loop54Exception(String message) {
        super(message);
    }

    public Loop54Exception(Exception cause) {
        super(cause);
    }

    public Loop54Exception(String message, Exception cause) {
        super(message, cause);
    }
}
