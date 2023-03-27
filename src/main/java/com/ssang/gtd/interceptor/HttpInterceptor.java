package com.ssang.gtd.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.io.BufferedReader;
import java.io.IOException;

@Component
public class HttpInterceptor implements HandlerInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(HttpInterceptor.class);
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)throws Exception{
        String reqUri = request.getRequestURI().trim();
        String payload = extractPayload(request);
        if(logger.isDebugEnabled() && !reqUri.startsWith("/admin")){
            logger.debug("#### preHandle : request URI : {}",reqUri);
            if(payload != null && !payload.isEmpty()) {
                logger.debug("#### preHandle : payload : {}",payload);
            }
        }
        if(reqUri.startsWith("/error")){
            //에러는 무시
            return true;
        }
        return true;
    }
    private String extractPayload(HttpServletRequest request) throws IOException {
        BufferedReader reader = request.getReader();
        StringBuilder stringBuilder = new StringBuilder();
        String line = null;
        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line);
        }
        return stringBuilder.toString();
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView mv)throws Exception{
        String reqUri = request.getRequestURI().trim();
        if(logger.isDebugEnabled()){
            if(!reqUri.startsWith("/product")){
                logger.debug("#### preHandle : request URI : {}",reqUri);
            }
        }
    }
}