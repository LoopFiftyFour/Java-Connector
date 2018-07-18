package com.loop54.spring;

import com.loop54.user.IRemoteClientInfo;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

/**
 * This class uses the HttpServletRequest and HttpServletResponse to serve user data to the Loop54 library.
 */
public class SpringRemoteClientInfo implements IRemoteClientInfo {
    private final HttpServletRequest request;
    private final HttpServletResponse response;
    private final Map<String, Cookie> cookieMap = new HashMap<>();

    public SpringRemoteClientInfo(ServletRequestAttributes requestAttributes){
        this.request = requestAttributes.getRequest();
        this.response = requestAttributes.getResponse();

        Cookie[] requestCookies = this.request.getCookies();
        if(requestCookies != null) {
            for (Cookie cookie : requestCookies) {
                cookieMap.put(cookie.getName(), cookie);
            }
        }
    }

    @Override
    public String getRequestHeader(String name) {
        return request.getHeader(name);
    }

    @Override
    public String getReferrer() {
        return request.getHeader("referer");
    }

    @Override
    public String getUserAgent() {
        return request.getHeader("user-agent");
    }

    @Override
    public String getRemoteIp() {
        return request.getRemoteAddr();
    }

    @Override
    public String getCookie(String name) {
        Cookie cookie = cookieMap.get(name);

        if(cookie == null)
            return null;

        return cookie.getValue();
    }

    @Override
    public void setCookie(String name, String value, LocalDateTime expiryTime) {
        Cookie cookie = new Cookie(name, value);
        cookie.setMaxAge((int)ChronoUnit.SECONDS.between(LocalDateTime.now(), expiryTime));

        response.addCookie(cookie);
        cookieMap.put(name, cookie);
    }
}
