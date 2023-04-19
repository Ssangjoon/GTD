package com.ssang.gtd.user.controller;

import com.ssang.gtd.user.dto.MemberDto;
import com.ssang.gtd.user.service.MemberService;
import com.ssang.gtd.utils.TokenInfoVO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class LoginController {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final MemberService memberService;

    public LoginController(MemberService memberService) {
        this.memberService = memberService;
    }


    @PostMapping("login")
    public ResponseEntity<TokenInfoVO> login(@RequestBody MemberDto dto, HttpServletResponse response, HttpServletRequest request) {
        ResponseEntity<TokenInfoVO> tokenInfoVO = memberService.login(dto);
        return tokenInfoVO;
    }
    @GetMapping("/logout")
    public int logout(HttpServletResponse response, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return 1;
    }
}
