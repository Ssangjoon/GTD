package com.ssang.gtd.oauth2;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class OAuth2LoginFailureHandler implements AuthenticationFailureHandler {
    private final Logger log = LoggerFactory.getLogger(getClass());
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.getWriter().write("소셜 로그인 실패! 서버 로그를 확인해주세요.");
        log.info("소셜 로그인에 실패했습니다. 에러 메시지 : {}", exception.getMessage());
    }
}
