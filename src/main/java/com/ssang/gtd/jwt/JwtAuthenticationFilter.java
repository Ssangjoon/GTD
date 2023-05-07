package com.ssang.gtd.jwt;

import com.ssang.gtd.auth.JwtService;
import com.ssang.gtd.entity.Member;
import com.ssang.gtd.user.dao.MemberRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String ACCESS = "access";
    public static final String REFRESH = "refresh";

    private final TokenProvider jwtTokenProvider;
    private final RedisTemplate redisTemplate;
    private final MemberRepository memberRepository;
    private final JwtService jwtService;

    private GrantedAuthoritiesMapper authoritiesMapper = new NullAuthoritiesMapper();
    /**
     *  헤더에서 jwt 토큰을 추출하고
     *  리프레시 토큰이 있을 경우 checkRefreshTokenAndReIssueAccessToken 메서드 호출한다.
     *  아닐 경우 access 토큰의 유효성 검증 후 인증 객체를 SecurityContextHolder에 저장한다
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException, IOException {
        logger.info("JwtAuthenticationFilter => doFilter 실행");
        // 1. Request Header 에서 JWT 토큰 추출
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletReponse = (HttpServletResponse) response;

        String refreshToken = jwtService.extractRefreshToken(httpServletRequest).orElse(null);

        // 2. 클라이언트가 RefreshToken를 보냈다면 DB에 저장된 토큰과 일치하는지 판단 후 일치한다면 AccessToken을 재발급한다.

        String accessToken = jwtService.extractAccessToken(httpServletRequest).orElse(null);
        String requestURI = httpServletRequest.getRequestURI();

        // 3. validateToken 으로 토큰 유효성 검사
        if (StringUtils.hasText(accessToken) && accessToken != null && jwtTokenProvider.validateToken(accessToken)) {

            String isLogout = (String) redisTemplate.opsForValue().get(accessToken);

            if (ObjectUtils.isEmpty(isLogout)) {

                // 토큰이 유효할 경우 토큰에서 Authentication 객체를 가지고 와서 SecurityContext 에 저장
                // 이부분이 선행
                Authentication authentication = jwtTokenProvider.getAuthentication(accessToken);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                logger.info("Scurity Context에 '{}' 인증 정보를 저장했습니다. uri: {}", authentication.getName(), requestURI);

                if(refreshToken != null){
                    checkRefreshTokenAndReIssueAccessToken(httpServletReponse,refreshToken);
                    return;
                }
            }else{
                logger.info("isLogout => " + isLogout);
            }
        }else{
            logger.info("유효한 JWT 토큰이 없습니다., uri:{}", requestURI);
        }
        chain.doFilter(request, response);
    }

/*    private void checkRefreshTokenAndReIssueAccessToken(HttpServletResponse response, String refreshToken) {
        memberRepository.findByRefreshToken(refreshToken).ifPresent(
                member -> jwtService.sendAccessToken(response, jwtService.createAccessToken())
        );
    }*/
    /**
     *  [리프레시 토큰으로 유저 정보 찾기 & 액세스 토큰/리프레시 토큰 재발급 메소드]
     *  파라미터로 들어온 헤더에서 추출한 리프레시 토큰으로 DB에서 유저를 찾고, 해당 유저가 있다면
     *  JwtService.createAccessToken()으로 AccessToken 생성,
     *  reIssueRefreshToken()로 리프레시 토큰 재발급 & DB에 리프레시 토큰 업데이트 메소드 호출
     *  그 후 JwtService.sendAccessTokenAndRefreshToken()으로 응답 헤더에 보내기
     */
    public void checkRefreshTokenAndReIssueAccessToken(HttpServletResponse response, String refreshToken) {
        memberRepository.findByRefreshToken(refreshToken)
                .ifPresent(member -> {
                    // 리프레시 토큰은 왜 제발급 해야 하지?
                    String reIssuedRefreshToken = reIssueRefreshToken(member);
                    jwtService.sendAccessAndRefreshToken(response, jwtService.createToken(ACCESS),
                            reIssuedRefreshToken);
                });
    }
    /**
     * [리프레시 토큰 재발급 & DB에 리프레시 토큰 업데이트 메소드]
     * jwtService.createRefreshToken()으로 리프레시 토큰 재발급 후
     * DB에 재발급한 리프레시 토큰 업데이트 후 Flush
     */
    private String reIssueRefreshToken(Member member) {
        String reIssuedRefreshToken = jwtService.createToken(REFRESH);
        //member.updateToken(reIssuedRefreshToken);
        memberRepository.saveAndFlush(member);
        return reIssuedRefreshToken;
    }
}