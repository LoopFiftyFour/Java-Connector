package com.loop54.model.response;

/** Response returned by the engine when an error has occurred. */
public class ErrorResponse extends Response {
    /** Details about the error. */
    public ErrorDetails error;
}
