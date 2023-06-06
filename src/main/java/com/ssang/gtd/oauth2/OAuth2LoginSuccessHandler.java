package com.ssang.gtd.oauth2;

import com.ssang.gtd.entity.MemberSocial;
import com.ssang.gtd.jwt.JwtService;
import com.ssang.gtd.jwt.TokenProvider;
import com.ssang.gtd.redis.RedisDao;
import com.ssang.gtd.user.dao.MemberRepository;
import com.ssang.gtd.user.dao.MemberSocialTypeRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static com.ssang.gtd.jwt.JwtConstants.ACCESS_TOKEN_HEADER;
import static com.ssang.gtd.jwt.JwtConstants.REFRESH_TOKEN_HEADER;

@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final JwtService jwtService;
    private final TokenProvider tokenProvider;
    private final MemberRepository memberRepository;
    private final MemberSocialTypeRepository memberSocialTypeRepository;
    private final RedisDao redisDao;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("OAuth2 Login 성공!");
        try {
            CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();

            // === User의 Role이 GUEST일 경우 처음 요청한 회원이므로 회원가입 페이지로 리다이렉트 === //
            if(oAuth2User.getRole() == Role.GUEST) {
                String accessToken = tokenProvider.createToken(authentication);
                response.addHeader(ACCESS_TOKEN_HEADER, "Bearer " + accessToken);
                response.sendRedirect("oauth2/sign-up"); // 프론트의 회원가입 추가 정보 입력 폼으로 리다이렉트

                jwtService.sendAccessAndRefreshToken(response,accessToken,null);
                MemberSocial findUser = memberSocialTypeRepository.findByEmail(oAuth2User.getEmail())
                                .orElseThrow(() -> new IllegalArgumentException("이메일에 해당하는 유저가 없습니다."));
                findUser.authorizeUser();
            } else {
                loginSuccess(response, oAuth2User, authentication); // 로그인에 성공한 경우 access, refresh 토큰 생성
            }
        } catch (Exception e) {
            throw e;
        }

    }

    private void loginSuccess(HttpServletResponse response, CustomOAuth2User oAuth2User, Authentication authentication) throws IOException {
        String accessToken = tokenProvider.createToken(authentication);
        String refreshToken = tokenProvider.generateRefreshToken();
        response.addHeader(ACCESS_TOKEN_HEADER, "Bearer " + accessToken);
        response.addHeader(REFRESH_TOKEN_HEADER, "Bearer " + refreshToken);

        jwtService.sendAccessAndRefreshToken(response, accessToken, refreshToken);
        redisDao.setValues(oAuth2User.getEmail(), refreshToken);
    }
}
