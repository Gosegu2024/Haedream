package com.haedream.haedream.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsMvcConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(@SuppressWarnings("null") CorsRegistry corsRegistry) {

        // 모든 Controller 경로에 요청프론트에서 오는 주소를 입력
        corsRegistry.addMapping("/**")
                .allowedOrigins("http://localhost:8088");
    }
}
