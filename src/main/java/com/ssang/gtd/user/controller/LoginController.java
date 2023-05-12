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
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static com.ssang.gtd.jwt.JwtConstants.AT_HEADER;
import static com.ssang.gtd.jwt.JwtConstants.RT_HEADER;


@RestController
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

        Map<String, String> tokens = accountService.refresh(request);

        response.setHeader(AT_HEADER, tokens.get(AT_HEADER));

        if (tokens.get(RT_HEADER) != null) {
            response.setHeader(RT_HEADER, tokens.get(RT_HEADER));
        }

        return ResponseEntity.ok(tokens);
    }
    public String index() {

        return "index";
    }
}
