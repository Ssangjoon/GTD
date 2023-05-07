package com.ssang.gtd.user.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.ssang.gtd.entity.Member;
import com.ssang.gtd.user.dao.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.ssang.gtd.jwt.JwtConstants.*;

@RequiredArgsConstructor
@Service
public class AccountServiceImpl implements AccountService{
    @Value("${jwt.secret}")
    private String secretKey;
    private final MemberRepository memberRepository;
    @Override
    @Transactional
    public void updateRefreshToken(String username, String refreshToken) {
        Member member = memberRepository.findByUserName(username).orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
        member.updateRefreshToken(refreshToken);
    }
    @Override
    public Map<String, String> refresh(String refreshToken) {

        // === Refresh Token 유효성 검사 === //
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secretKey)).build();
        DecodedJWT decodedJWT = verifier.verify(refreshToken);

        // === Access Token 재발급 === //
        long now = System.currentTimeMillis();
        String username = decodedJWT.getSubject();
        Member member = memberRepository.findByUserName(username)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
        if (!member.getRefreshToken().equals(refreshToken)) {
            throw new JWTVerificationException("유효하지 않은 Refresh Token 입니다.");
        }
        String accessToken = JWT.create()
                .withSubject(member.getUserName())
                .withExpiresAt(new Date(now + AT_EXP_TIME))
                /*.withClaim("roles", member.getRoles().stream().map(Role::getName)
                        .collect(Collectors.toList()))*/
                .sign(Algorithm.HMAC256(secretKey));
        Map<String, String> accessTokenResponseMap = new HashMap<>();

        // === 현재시간과 Refresh Token 만료날짜를 통해 남은 만료기간 계산 === //
        // === Refresh Token 만료시간 계산해 1개월 미만일 시 refresh token도 발급 === //
        long refreshExpireTime = decodedJWT.getClaim("exp").asLong() * 1000;
        long diffDays = (refreshExpireTime - now) / 1000 / (24 * 3600);
        long diffMin = (refreshExpireTime - now) / 1000 / 60;
        if (diffMin < 5) {
            String newRefreshToken = JWT.create()
                    .withSubject(member.getUserName())
                    .withExpiresAt(new Date(now + RT_EXP_TIME))
                    .sign(Algorithm.HMAC256(secretKey));
            accessTokenResponseMap.put(RT_HEADER, newRefreshToken);
            member.updateRefreshToken(newRefreshToken);
        }

        accessTokenResponseMap.put(AT_HEADER, accessToken);
        return accessTokenResponseMap;
    }
}
