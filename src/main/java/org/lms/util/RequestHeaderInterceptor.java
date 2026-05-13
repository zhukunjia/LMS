package org.lms.util;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class RequestHeaderInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        RequestHeaderInfo info = new RequestHeaderInfo();

        info.setToken(request.getHeader("x-lms-token"));
        info.setUserId(request.getHeader("x-lms-userId"));

        RequestContextHolder.set(info);

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        RequestContextHolder.clear();
    }
}
