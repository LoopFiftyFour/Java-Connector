package com.loop54;

import java.util.IllegalFormatException;
import java.util.UUID;

public class Utils {
    /** Trims and validates a URL. */
    public static String fixEngineUrl(String url) {
        String fixed = url.toLowerCase().trim().replaceAll("\\\\", "/");

        if (!url.startsWith("http://") && !url.startsWith("https://"))
            throw new IllegalArgumentException("Invalid url: Url must use protocol http or https.");

        return fixed.endsWith("/") ? fixed.substring(0, fixed.length() - 1) : fixed;
    }

    /** Checks whether an URL is using HTTPS. */
    public static boolean urlIsHttps(String url) {
        return url.startsWith("https://");
    }

    /** Generates a new random UserId. */
    public static String generateUserId() {
        return UUID.randomUUID().toString();
    }
}
