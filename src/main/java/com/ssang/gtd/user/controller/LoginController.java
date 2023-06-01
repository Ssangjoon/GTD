package com.ssang.gtd.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssang.gtd.jwt.TokenProvider;
import com.ssang.gtd.user.dto.LoginReq;
import com.ssang.gtd.user.dto.TokenReissueDto;
import com.ssang.gtd.user.service.AccountService;
import com.ssang.gtd.user.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

import static com.ssang.gtd.jwt.JwtConstants.ACCESS_TOKEN_HEADER;
import static com.ssang.gtd.jwt.JwtConstants.REFRESH_TOKEN_HEADER;


@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class LoginController {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final MemberService memberService;
    private final AccountService accountService;
    private final AuthenticationManager authenticationManager;
    private final TokenProvider tokenProvider;

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
    public ResponseEntity<TokenReissueDto> refresh(HttpServletRequest request, HttpServletResponse response) {

        TokenReissueDto reissueDto = accountService.refresh(request);

        response.setHeader(ACCESS_TOKEN_HEADER, reissueDto.getAccessToken());

        if (reissueDto.getRefreshToken() != null) {
            response.setHeader(REFRESH_TOKEN_HEADER, reissueDto.getRefreshToken());
        }

        return ResponseEntity.ok(reissueDto);
    }
    @PostMapping("/oauth/token")
    public String generateToken(HttpServletRequest request){
        ObjectMapper om = new ObjectMapper();

        try {

            LoginReq login = om.readValue(request.getInputStream(), LoginReq.class);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(login.getUsername(),login.getPassword());

            System.out.println(authenticationManager.authenticate(authenticationToken));
            return tokenProvider.createToken(authenticationManager.authenticate(authenticationToken));

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
