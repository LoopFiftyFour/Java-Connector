package com.loop54.exceptions;

/** Base class for all Loop54-specific unchecked exceptions. */
public class Loop54RuntimeException extends RuntimeException {
    public Loop54RuntimeException(String message) {
        super(message);
    }

    public Loop54RuntimeException(Exception cause) {
        super(cause);
    }

    public Loop54RuntimeException(String message, Exception cause) {
        super(message, cause);
    }
}
