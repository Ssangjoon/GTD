package com.ssang.gtd.global.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssang.gtd.global.jwt.JwtService;
import com.ssang.gtd.global.jwt.TokenProvider;
import com.ssang.gtd.global.redis.RedisDao;
import com.ssang.gtd.domain.user.service.AccountService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import static com.ssang.gtd.global.jwt.JwtConstants.ACCESS_TOKEN_HEADER;
import static com.ssang.gtd.global.jwt.JwtConstants.REFRESH_TOKEN_HEADER;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
@RequiredArgsConstructor
@Primary
@Component
public class AuthenticationSuccessHandlerCustom implements AuthenticationSuccessHandler {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final AccountService accountService;
    private final TokenProvider tokenProvider;
    private final RedisDao redisDao;
    private final JwtService jwtService;
    @Value("${jwt.access.expiration}")
    private String accessTokenExpiration;
    @Value("${jwt.secret}")
    private String secretKey;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        String username = extractUsername(authentication);

        String accessToken = tokenProvider.createToken(authentication);
        String refreshToken = tokenProvider.generateRefreshToken();

        // username을 키로 하여 리프레시 토큰을 저장한다.
        redisDao.setValues(username, refreshToken, Duration.ofDays(14));
        log.info("redis key:{} , value: {} ==> 저장하였습니다.", username,refreshToken);

        // Access Token , Refresh Token 프론트 단에 Response Header로 전달
        response.setContentType(APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("utf-8");

        jwtService.sendAccessAndRefreshToken(response,accessToken,refreshToken);

        Map<String, String> responseMap = new HashMap<>();
        responseMap.put(ACCESS_TOKEN_HEADER, accessToken);
        responseMap.put(REFRESH_TOKEN_HEADER, refreshToken);

        log.info("로그인에 성공하였습니다. id : {}", username);
        log.info("로그인에 성공하였습니다. AccessToken : {}", accessToken);
        log.info("발급된 AccessToken 만료 기간 : {}", accessTokenExpiration);

        new ObjectMapper().writeValue(response.getWriter(), responseMap);
    }

    private String extractUsername(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return userDetails.getUsername();
    }

}