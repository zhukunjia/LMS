package org.lms.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.lms.app.TokenApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class TokenInterceptor implements HandlerInterceptor {

    @Autowired
    private TokenApplication tokenApplication;

    /**
     * 白名单：不需要校验token的接口
     */
    private static final String[] WHITE_LIST = {
            "/v1/user/login",
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/v3/api-docs/**",
            "/webjars/**",
            "/error",
            "/error/**"
    };

    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        String path = request.getServletPath();

        if (isWhiteList(path)) {
            return true;
        }

        String userId = RequestContextHolder.getUserId();
        String token = RequestContextHolder.getToken();

        if (StringUtils.isEmpty(userId) || StringUtils.isEmpty(token)) {
            ServiceData<Object> serviceData = ServiceData.fail(RetCode.AUTH_ERROR.getCode(), "userId or token is empty");
            response.getWriter().write(JSONObject.toJSONString(serviceData));

            return false;
        }

        if (tokenApplication.checkToken(userId, token)) {
            return true;
        } else {
            ServiceData<Object> serviceData = ServiceData.fail(RetCode.AUTH_ERROR.getCode(), "token has expired");
            response.getWriter().write(JSONObject.toJSONString(serviceData));

            return false;
        }
    }

    /**
     * 判断是否白名单
     */
    private boolean isWhiteList(String path) {
        for (String pattern : WHITE_LIST) {
            if (pathMatcher.match(pattern, path)) {
                return true;
            }
        }
        return false;
    }
}
