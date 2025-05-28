package com.saas.subscription.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/api/v1/css/**")
                .addResourceLocations("classpath:/static/css/");
        registry.addResourceHandler("/api/v1/js/**")
                .addResourceLocations("classpath:/static/js/");
    }
} 