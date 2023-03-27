package com.ssang.gtd.config;

import com.ssang.gtd.interceptor.HttpInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfiguration implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry
                .addInterceptor(new HttpInterceptor())
                .addPathPatterns("/**/collection*","/**/material*","/**/member*")
                .excludePathPatterns("");
    }
}
