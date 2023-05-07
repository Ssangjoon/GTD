package com.ssang.gtd.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssang.gtd.exception.ErrorCode;
import com.ssang.gtd.exception.ErrorResponse;
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
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static com.ssang.gtd.jwt.JwtConstants.TOKEN_HEADER_PREFIX;
import static jakarta.servlet.http.HttpServletResponse.SC_BAD_REQUEST;
import static jakarta.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomAuthorizationFilter extends OncePerRequestFilter {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Value("${jwt.secret}")
    private String secretKey;
    private final TokenProvider jwtTokenProvider;
    @NoLogging
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String servletPath = request.getServletPath();
        String authrizationHeader = request.getHeader(AUTHORIZATION);

        // 1. 로그인, 리프레시 요청이라면 토큰 검사하지 않음
        if (servletPath.equals("/login") || servletPath.equals("/refresh")) {
            filterChain.doFilter(request, response);
        } else if (authrizationHeader == null || !authrizationHeader.startsWith(TOKEN_HEADER_PREFIX)) {
            //2. Header를 확인하여 토큰값이 없거나 정상적이지 않다면 400 오류
            log.info("CustomAuthorizationFilter : JWT Token이 존재하지 않습니다.");
            response.setStatus(SC_BAD_REQUEST);
            response.setContentType(APPLICATION_JSON_VALUE);
            response.setCharacterEncoding("utf-8");
            new ObjectMapper().writeValue(response.getWriter(), ErrorResponse.toResponseEntity(ErrorCode.UNAUTORIZED));
        } else {
            try {
                // Access Token만 꺼내옴
                String accessToken = authrizationHeader.substring(TOKEN_HEADER_PREFIX.length());
                String requestURI = request.getRequestURI();

                Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(accessToken);


                // === Access Token 내 Claim에서 Authorities 꺼내 Authentication 객체 생성 & SecurityContext에 저장 === //
                Authentication authentication = jwtTokenProvider.getAuthentication(accessToken);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                logger.info("Scurity Context에 '{}' 인증 정보를 저장했습니다. uri: {}", authentication.getName(), requestURI);


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
        }
    }
}