package com.loop54.model.response;

/** Detailed information regarding an error returned from the engine. */
public class ErrorDetails {
    /** The HTTP status code of the response. */
    public int code;

    /** The HTTP status code of the response. */
    public String status;

    /** The name of the error. */
    public String title;

    /** The more detailed information about the error. Note: not always shown. */
    public String detail;

    /** The input parameter, if any, that caused the error. */
    public String parameter;
}
