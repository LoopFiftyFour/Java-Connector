package com.loop54.user;

import com.loop54.user.NullClientInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserMetaDataTest {
    @Test
    public void setSetFromClientInfo() {
        com.loop54.user.UserMetaData meta = new com.loop54.user.UserMetaData();
        meta.setFromClientInfo(new NullClientInfo());
    }

    @Test
    public void setSetFromClientInfoFillWithData() {
        com.loop54.user.UserMetaData meta = new com.loop54.user.UserMetaData();

        String remoteIp = "127.0.0.1";
        String referer = "referer.se";
        String userAgent = "testcase";

        NullClientInfo clientInfo = new NullClientInfo() {
            @Override public String getRemoteIp() { return remoteIp; }
            @Override public String getReferrer() { return referer; }
            @Override public String getUserAgent() { return userAgent; }
        };

        meta.setFromClientInfo(clientInfo);

        Assertions.assertEquals(remoteIp, meta.ipAddress);
        Assertions.assertEquals(referer, meta.referer);
        Assertions.assertEquals(userAgent, meta.userAgent);
    }

    @Test
    public void setSetFromClientInfoFillWithDataProxyIp() {
        com.loop54.user.UserMetaData meta = new com.loop54.user.UserMetaData();

        String remoteIp = "127.0.0.1";
        String proxyIp = "127.0.0.2";
        String referer = "referer.se";
        String userAgent = "testcase";

        NullClientInfo clientInfo = new NullClientInfo() {
            @Override public String getRemoteIp() { return remoteIp; }
            @Override public String getReferrer() { return referer; }
            @Override public String getUserAgent() { return userAgent; }
        };
        clientInfo.headers.put(com.loop54.user.UserMetaData.PROXY_IP_HEADER_NAME, proxyIp);

        meta.setFromClientInfo(clientInfo);

        Assertions.assertEquals(proxyIp, meta.ipAddress);
        Assertions.assertEquals(referer, meta.referer);
        Assertions.assertEquals(userAgent, meta.userAgent);
    }

    @Test
    public void setSetFromClientInfoFillWithExistingUserCookie() {
        com.loop54.user.UserMetaData meta = new com.loop54.user.UserMetaData();

        String remoteIp = "127.0.0.1";
        String referer = "referer.se";
        String userAgent = "testcase";
        String userId = "test.user";

        NullClientInfo clientInfo = new NullClientInfo() {
            @Override public String getRemoteIp() { return remoteIp; }
            @Override public String getReferrer() { return referer; }
            @Override public String getUserAgent() { return userAgent; }
        };
        clientInfo.setCookie(com.loop54.user.UserMetaData.USER_ID_COOKIE_KEY, userId, LocalDateTime.now().plusYears(1));

        meta.setFromClientInfo(clientInfo);

        Assertions.assertEquals(remoteIp, meta.ipAddress);
        Assertions.assertEquals(referer, meta.referer);
        Assertions.assertEquals(userAgent, meta.userAgent);
        Assertions.assertEquals(userId, meta.userId);
    }

    @Test
    public void setSetFromClientInfoFillWithExistingUserOnMetaData() {
        com.loop54.user.UserMetaData meta = new com.loop54.user.UserMetaData();

        String remoteIp = "127.0.0.1";
        String referer = "referer.se";
        String userAgent = "testcase";
        String userId = "test.user";

        NullClientInfo clientInfo = new NullClientInfo() {
            @Override public String getRemoteIp() { return remoteIp; }
            @Override public String getReferrer() { return referer; }
            @Override public String getUserAgent() { return userAgent; }
        };

        meta.userId = userId;
        meta.setFromClientInfo(clientInfo);

        Assertions.assertEquals(remoteIp, meta.ipAddress);
        Assertions.assertEquals(referer, meta.referer);
        Assertions.assertEquals(userAgent, meta.userAgent);
        Assertions.assertEquals(userId, meta.userId);
    }
}
