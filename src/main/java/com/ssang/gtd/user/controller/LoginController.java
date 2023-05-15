package com.ssang.gtd.user.controller;

import com.ssang.gtd.user.dto.TokenReissueDto;
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

import static com.ssang.gtd.jwt.JwtConstants.ACCESS_TOKEN_HEADER;
import static com.ssang.gtd.jwt.JwtConstants.REFRESH_TOKEN_HEADER;


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
    @PostMapping("/api/logout")
    public int logout(HttpServletResponse response, HttpServletRequest request) {
        memberService.logout(request);
        return 1;
    }
    @PostMapping("/refresh")
    public ResponseEntity<TokenReissueDto> refresh(HttpServletRequest request, HttpServletResponse response) {

        TokenReissueDto reissueDto = accountService.refresh(request);

        response.setHeader(ACCESS_TOKEN_HEADER, reissueDto.getAccessToken());

        if (reissueDto.getRefreshToken() != null) {
            response.setHeader(REFRESH_TOKEN_HEADER, reissueDto.getRefreshToken());
        }

        return ResponseEntity.ok(reissueDto);
    }

}
