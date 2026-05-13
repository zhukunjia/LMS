package org.lms.config;

import org.lms.util.RequestHeaderInterceptor;
import org.lms.util.TokenInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class LmsWebConfig implements WebMvcConfigurer {

    private final RequestHeaderInterceptor requestHeaderInterceptor;

    private final TokenInterceptor tokenInterceptor;

    public LmsWebConfig(RequestHeaderInterceptor requestHeaderInterceptor, TokenInterceptor tokenInterceptor) {
        this.requestHeaderInterceptor = requestHeaderInterceptor;
        this.tokenInterceptor = tokenInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(requestHeaderInterceptor)
                .addPathPatterns("/**");

        registry.addInterceptor(tokenInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/v1/user/login",
                        "/swagger-ui/**",
                        "/swagger-ui.html",
                        "/v3/api-docs/**",
                        "/webjars/**",
                        "/error",
                        "/error/**");
    }
}