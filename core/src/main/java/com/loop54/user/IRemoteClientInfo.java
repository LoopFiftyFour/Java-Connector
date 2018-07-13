package com.loop54.user;

import java.time.LocalDateTime;

/** This interface contains methods used to get information about the end-user client request. */
public interface IRemoteClientInfo {
    /**
     * Get a named header from the request made by the end-user.
     * @return If the header isn't set return null, otherwise the string value of the header.
     */
    String getRequestHeader(String name);

    /**
     * Get the Referer(sic) header sent by the end-user.
     * @return Referer header or null if it doesn't exist.
     */
    String getReferrer();

    /**
     * Get the User-Agent header sent by the end-user.
     * @return User-Agent header or null if it doesn't exist.
     */
    String getUserAgent();

    /**
     * Get the IP address of the end-user.
     * @return IP address or null if it doesn't exist.
     */
    String getRemoteIp();

    /**
     * Gets the value of a cookie with a given name.
     * This needs to be able return cookies set with {@link #setCookie(String, String, LocalDateTime)}
     * @return Return the cookie data or null if it doesn't exist.
     */
    String getCookie(String name);

    /** Sets a cookie with a given name, value and expiryTime. Cookies set by this method needs to be accessible with {@link #getCookie(String)}; */
    void setCookie(String name, String value, LocalDateTime expiryTime);
}
