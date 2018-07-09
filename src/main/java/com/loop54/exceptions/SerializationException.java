package com.loop54.exceptions;

/** Thrown when the data returned from the engine could not be serialized or deserialize. */
public class SerializationException extends Loop54Exception {
    public SerializationException(String message, Exception cause) {
        super(message, cause);
    }
}