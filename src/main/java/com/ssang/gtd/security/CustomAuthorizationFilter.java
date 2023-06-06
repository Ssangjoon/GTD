package com.ssang.gtd.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssang.gtd.exception.ErrorCode;
import com.ssang.gtd.exception.ErrorResponse;
import com.ssang.gtd.jwt.TokenProvider;
import com.ssang.gtd.redis.RedisDao;
import com.ssang.gtd.utils.anno.NoLogging;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.thymeleaf.util.StringUtils;

import java.io.IOException;
import java.util.Arrays;

import static com.ssang.gtd.jwt.JwtConstants.TOKEN_HEADER_PREFIX;
import static jakarta.servlet.http.HttpServletResponse.SC_BAD_REQUEST;
import static jakarta.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Component
    @RequiredArgsConstructor
    public class CustomAuthorizationFilter extends OncePerRequestFilter {
    private final Logger log = LoggerFactory.getLogger(getClass());
    @Value("${jwt.secret}")
    private String secretKey;
    private final TokenProvider jwtTokenProvider;
    private final RedisDao redisDao;
    @NoLogging
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String servletPath = request.getServletPath();
        String authrizationHeader = request.getHeader(AUTHORIZATION);

        //1. Header를 확인하여 토큰값이 없거나 정상적이지 않다면 400 오류
        if (StringUtils.isEmpty(authrizationHeader) || !authrizationHeader.startsWith(TOKEN_HEADER_PREFIX)) {

            log.info("CustomAuthorizationFilter : JWT Token이 존재하지 않습니다.");
            response.setStatus(SC_BAD_REQUEST);
            response.setContentType(APPLICATION_JSON_VALUE);
            response.setCharacterEncoding("utf-8");
            new ObjectMapper().writeValue(response.getWriter(), ErrorResponse.toResponseEntity(ErrorCode.UNAUTORIZED));

        } else {
            try {
                // === Access Token만 꺼내옴 === //
                String accessToken = authrizationHeader.substring(TOKEN_HEADER_PREFIX.length());
                String requestURI = request.getRequestURI();

                // === Access Token 검증 === //
                Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(accessToken);

                // === Access Token 내 Claim에서 Authorities 꺼내 Authentication 객체 생성  === //
                Authentication authentication = jwtTokenProvider.getAuthentication(accessToken);

                // === 해당 토큰으로 등록된 블랙리스트가 없다면 & SecurityContext에 저장 === //
                if(redisDao.getValues(accessToken) == null){
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    log.info("Scurity Context에 '{}' 인증 정보를 저장했습니다. uri: {}", authentication.getName(), requestURI);
                }

            } catch (ExpiredJwtException e) {

                log.info("CustomAuthorizationFilter : Access Token이 만료되었습니다.");
                response.setStatus(SC_UNAUTHORIZED);
                response.setContentType(APPLICATION_JSON_VALUE);
                response.setCharacterEncoding("utf-8");
                new ObjectMapper().writeValue(response.getWriter(), ErrorResponse.toResponseEntity(ErrorCode.UNAUTORIZED));

            } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {

                log.info("CustomAuthorizationFilter : JWT 토큰이 잘못되었습니다. message : {}", e.getMessage());
                response.setStatus(SC_BAD_REQUEST);
                response.setContentType(APPLICATION_JSON_VALUE);
                response.setCharacterEncoding("utf-8");
                new ObjectMapper().writeValue(response.getWriter(), ErrorResponse.toResponseEntity(ErrorCode.UNAUTORIZED));

            } catch (UnsupportedJwtException e) {
                log.info("지원되지 않는 JWT 토큰입니다.");
            } catch (IllegalArgumentException e) {
                log.info("JWT 토큰이 잘못되었습니다.");
            }

            // 인증처리 후 정상적으로 다음 filter 수행
            filterChain.doFilter(request, response);
        }
    }
    @NoLogging
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request)
            throws ServletException {
        // excludePath에 해당하는 요청이라면 토큰 검사하지 않음
        log.info("shouldNotFilter 실행");
        String path = request.getRequestURI();
        String[] excludePath = {"/api/login","/api/joinUp","/api/refresh","/api/oauth/token","/docs","/index","/login","/test/enums"};

        return Arrays.stream(excludePath).anyMatch(path::startsWith);
    }
}