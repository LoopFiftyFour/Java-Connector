package com.loop54.exceptions;

/** Thrown when the we cannot read the client info properly. */
public class ClientInfoException extends Loop54Exception {
    public ClientInfoException(String message) {
        super(message);
    }
}
