package com.ssang.gtd.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

@Configuration
public class WebConfig {
    @Bean
    public CommonsRequestLoggingFilter requestLoggingFilter() {
        CommonsRequestLoggingFilter c = new CommonsRequestLoggingFilter();
        c.setIncludePayload(true);
        c.setIncludeClientInfo(true);
        return c;
    }
}
