package com.loop54.user;

import com.loop54.Utils;
import com.loop54.exceptions.ClientInfoException;
import com.loop54.exceptions.Loop54ArgumentException;
import com.loop54.exceptions.Loop54Exception;

import java.time.LocalDateTime;

/**
 * Represents data about the end-user. Used by the search engine to personalize the shopping experience.
 * Use this class to override the default behaviour of the library. For instance if you want a custom
 * UserId based on a logged in user.
 */
public class UserMetaData {
    public UserMetaData() { }

    /** The unique id of the user. Could be used for overriding the default, random, cookie-stored identifier. */
    public UserMetaData(String userId) {
        if (userId == null)
            throw new IllegalArgumentException("User id was null");

        this.userId = userId;
    }

    /**
     * An unique identifier of the end-user. DO NOT set this to a placeholder string. If it's not set it'll be automatically
     * set to a random GUID when making the request and a cookie will be stored to the IRemoteClientInfo provided in the
     * request so that we may identify the user later.
     */
    public String userId;

    /** Ip address of the end-user. If not set it will later be set to the IP address provided by the IRemoteClientInfo. */
    public String ipAddress;

    /** User-Agent header from the end-user. If not set it will later be set to the User-Agent provided by the IRemoteClientInfo. */
    public String userAgent;

    /** The Referer(sic) header from the end-user. If not set it will later be set to the Referer provided by the IRemoteClientInfo. */
    public String referer;

    private static boolean isNullOrEmpty(String s) {
        return s == null || s.isEmpty();
    }

    public void setFromClientInfo(IRemoteClientInfo clientInfo) {
        if (isNullOrEmpty(userId))
            userId = getOrAddUserIdCookie(clientInfo);

        assertUserIdNotNull();

        if (isNullOrEmpty(ipAddress))
            ipAddress = getRealIp(clientInfo);

        if (isNullOrEmpty(userAgent))
            userAgent = clientInfo.getUserAgent();

        if (isNullOrEmpty(referer))
            referer = clientInfo.getReferrer();
    }

    private void assertUserIdNotNull() {
        if (isNullOrEmpty(userId))
            throw new Loop54ArgumentException("UserId is null or empty. Make sure you've implemented the " + IRemoteClientInfo.class.getSimpleName() +
                    " interface properly so that the cookie setting and getting works.");
    }

    private String getOrAddUserIdCookie(IRemoteClientInfo cookieAccessor) {
        String cookieValue = cookieAccessor.getCookie(USER_ID_COOKIE_KEY);

        if (isNullOrEmpty(cookieValue)) {
            String newUserId = Utils.generateUserId();
            cookieAccessor.setCookie(USER_ID_COOKIE_KEY, newUserId, LocalDateTime.now().plusYears(1));
            cookieValue = cookieAccessor.getCookie(USER_ID_COOKIE_KEY);

            if (!newUserId.equals(cookieValue))
                throw new Loop54ArgumentException("The cookie with the name '" + USER_ID_COOKIE_KEY + "' (Value: '" + cookieValue + "') does not match the newly " +
                        "generated userId '" + newUserId + "'. Make sure you've implemented the " + IRemoteClientInfo.class.getSimpleName() + " interface " +
                        "properly so that the cookie setting and getting works.");
        }

        return cookieValue;
    }

    public static final String USER_ID_COOKIE_KEY = "Loop54User";
    public static final String PROXY_IP_HEADER_NAME = "X-Forwarded-For";

    private String getRealIp(IRemoteClientInfo clientInfo) {
        //behind proxy?
        String forwarded = clientInfo.getRequestHeader(PROXY_IP_HEADER_NAME);

        return isNullOrEmpty(forwarded) ? clientInfo.getRemoteIp() : forwarded;
    }
}
