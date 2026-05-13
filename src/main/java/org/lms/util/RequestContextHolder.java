package org.lms.util;

public class RequestContextHolder {

    private static final ThreadLocal<RequestHeaderInfo> CONTEXT = new ThreadLocal<>();

    public static void set(RequestHeaderInfo info) {
        CONTEXT.set(info);
    }

    public static RequestHeaderInfo get() {
        return CONTEXT.get();
    }

    public static void clear() {
        CONTEXT.remove();
    }

    public static String getToken() {
        RequestHeaderInfo info = get();
        return info == null ? null : info.getToken();
    }

    public static String getUserId() {
        RequestHeaderInfo info = get();
        return info == null ? null : info.getUserId();
    }
}