package com.loop54.test;

import com.loop54.user.IRemoteClientInfo;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class NullClientInfo implements IRemoteClientInfo {
    public Map<String, String> headers = new HashMap<>();

    @Override
    public String getRequestHeader(String name) { return headers.get(name); }

    private Map<String, String> cookies = new HashMap<>();

    @Override
    public void setCookie(String name, String value, LocalDateTime expiryTime) { cookies.put(name, value); }

    @Override
    public String getCookie(String name) {
        return cookies.get(name);
    }

    @Override
    public String getReferrer() {
        return null;
    }

    @Override
    public String getUserAgent() {
        return null;
    }

    @Override
    public String getRemoteIp() {
        return null;
    }
}
