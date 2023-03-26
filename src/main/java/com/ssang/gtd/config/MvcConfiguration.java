package com.ssang.gtd.config;

import com.ssang.gtd.interceptor.AdminAuthinterceptor;
import com.ssang.gtd.interceptor.Authinterceptor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfiguration implements WebMvcConfigurer {

    private static final Logger log = LogManager.getLogger(MvcConfiguration.class);

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        log.trace("MvcConfiguration.addInterceptors() 호출됨");

        // 이 메서드가 정의되어 있다면,
        // 스프링부트는 이 메서드를 호출하여 추가할 인터셉터의 정보를 InterceptorRegistry로 받는다.
        registry
                .addInterceptor(new Authinterceptor())
                .addPathPatterns("/**/add*", "/**/update*", "/**/delete*")
                .excludePathPatterns("/junho/midpoint/add","/**/admin/**/");

        registry
                .addInterceptor(new AdminAuthinterceptor())
                .addPathPatterns("/**/admin/**/*")
                .excludePathPatterns("/**/admin/login/*");
    }
}
