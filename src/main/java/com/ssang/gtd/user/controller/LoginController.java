package com.ssang.gtd.user.controller;

import com.ssang.gtd.user.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class LoginController {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final MemberService memberService;

    @PostMapping("/test")
    public String test(HttpServletRequest request) {
        return "success";
    }
    @PostMapping("/logout")
    public int logout(HttpServletResponse response, HttpServletRequest request) {
        memberService.logout(request);
        return 1;
    }

    @GetMapping("/oauth")
    public String index() {

        return "index";
    }
}
