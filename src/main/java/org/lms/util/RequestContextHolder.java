package org.lms.util;

public class RequestContextHolder {

    /**
     * 如果考虑一步线程场景，可以使用 TTL，即 TransmittableThreadLocal
     */
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