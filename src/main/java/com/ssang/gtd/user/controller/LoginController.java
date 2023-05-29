package com.ssang.gtd.user.controller;

import com.ssang.gtd.user.service.AccountService;
import com.ssang.gtd.user.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static com.ssang.gtd.jwt.JwtConstants.*;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;


@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class LoginController {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final MemberService memberService;
    private final AccountService accountService;

    @PostMapping("/test")
    public String test(HttpServletRequest request) {
        return "success";
    }
    @PostMapping("/logout")
    public int logout(HttpServletResponse response, HttpServletRequest request) {
        memberService.logout(request);
        return 1;
    }
    @PostMapping("/refresh")
    public ResponseEntity<Map<String, String>> refresh(HttpServletRequest request, HttpServletResponse response) {
        String authorizationHeader = request.getHeader(AUTHORIZATION);

        if (authorizationHeader == null || !authorizationHeader.startsWith(TOKEN_HEADER_PREFIX)) {
            throw new RuntimeException("JWT Token이 존재하지 않습니다.");
        }
        String refreshToken = authorizationHeader.substring(TOKEN_HEADER_PREFIX.length());
        Map<String, String> tokens = accountService.refresh(refreshToken);
        response.setHeader(ACCESS_TOKEN_HEADER, tokens.get(ACCESS_TOKEN_HEADER));
        if (tokens.get(REFRESH_TOKEN_HEADER) != null) {
            response.setHeader(REFRESH_TOKEN_HEADER, tokens.get(REFRESH_TOKEN_HEADER));
        }
        return ResponseEntity.ok(tokens);
    }
}
