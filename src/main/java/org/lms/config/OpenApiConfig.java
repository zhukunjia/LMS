package org.lms.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("图书管理系统(LMS) API 文档")
                        .description("Spring Boot 2.7.18 + springdoc-openapi + mybatis-plus")
                        .version("1.0.0"));
    }
}
