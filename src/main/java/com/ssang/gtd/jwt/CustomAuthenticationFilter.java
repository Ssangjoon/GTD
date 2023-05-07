package com.ssang.gtd.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssang.gtd.user.dto.LoginReq;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final AuthenticationManager authenticationManager;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        log.info("로그인 시도 : attemptAuthentication()");
        if(!request.getMethod().equals("POST")){
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }
        ObjectMapper om = new ObjectMapper();
        try {
            log.info("username, password 받기");
            LoginReq login = om.readValue(request.getInputStream(), LoginReq.class);
            log.info(login.toString());

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(login.getUsername(),login.getPassword());
            log.info("username : " + authenticationToken.getPrincipal().toString());
            log.info("password : " + authenticationToken.getCredentials().toString());
            log.info("=========================================================");

            log.info("정상적인 로그인 시도 여부를 검증한다.");
            log.info("=> Authentication start");
            Authentication authentication = authenticationManager.authenticate(authenticationToken);
            log.info("<= Authentication end");
            log.info("=========================================================");
            return authentication;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }
}