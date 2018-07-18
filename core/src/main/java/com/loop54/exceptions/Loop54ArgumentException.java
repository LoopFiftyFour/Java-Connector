package com.loop54.exceptions;

/** Runtime exception thrown when an illegal/missing argument is detected. */
public class Loop54ArgumentException extends Loop54RuntimeException {
    public Loop54ArgumentException(String message) {
        super(message);
    }
}
