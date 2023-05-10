package com.ssang.gtd.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssang.gtd.jwt.TokenProvider;
import com.ssang.gtd.redis.RedisDao;
import com.ssang.gtd.user.service.AccountService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import static com.ssang.gtd.jwt.JwtConstants.AT_HEADER;
import static com.ssang.gtd.jwt.JwtConstants.RT_HEADER;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
@RequiredArgsConstructor
@Component
public class AuthenticationSuccessHandlerCustom implements AuthenticationSuccessHandler {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final AccountService accountService;
    private final TokenProvider tokenProvider;
    private final RedisDao redisDao;
    @Value("${jwt.secret}")
    private String secretKey;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        User user = (User) authentication.getPrincipal();

        String accessToken = tokenProvider.generateAccessToken(authentication);
        String refreshToken = tokenProvider.generateRefreshToken();

        // Refresh Token DB에 저장
        //accountService.updateRefreshToken(user.getUsername(), refreshToken);
        // username을 키로 하여 리프레시 토큰을 저장한다.
        redisDao.setValues(user.getUsername(), refreshToken, Duration.ofDays(14));
        log.info("redis key:{} , value: {} ==> 저장하였습니다.", user.getUsername(),refreshToken);

        // Access Token , Refresh Token 프론트 단에 Response Header로 전달
        response.setContentType(APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("utf-8");
        response.setHeader(AT_HEADER, accessToken);
        response.setHeader(RT_HEADER, refreshToken);

        Map<String, String> responseMap = new HashMap<>();
        responseMap.put(AT_HEADER, accessToken);
        responseMap.put(RT_HEADER, refreshToken);
        new ObjectMapper().writeValue(response.getWriter(), responseMap);
    }


}