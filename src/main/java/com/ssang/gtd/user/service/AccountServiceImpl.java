package com.ssang.gtd.user.service;

import com.ssang.gtd.entity.Member;
import com.ssang.gtd.jwt.TokenProvider;
import com.ssang.gtd.redis.RedisDao;
import com.ssang.gtd.user.dao.MemberRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.ssang.gtd.jwt.JwtConstants.*;

@RequiredArgsConstructor
@Service
public class AccountServiceImpl implements AccountService{
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Value("${jwt.secret}")
    private String secretKey;
    private final MemberRepository memberRepository;
    private final TokenProvider jwtTokenProvider;
    private final RedisDao redisDao;

    @Override
    @Transactional
    public void updateRefreshToken(String username, String refreshToken) {
        Member member = memberRepository.findByUserName(username).orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
        member.updateRefreshToken(refreshToken);
    }

    @Override
    public Map<String, String> refresh(String refreshToken) {
        long now = System.currentTimeMillis();
        String accessToken = "";
        // Refresh Token 유효성 검사
        // refresh 토큰의 만료시간이 지나지 않았을 경우, 새로운 access 토큰을 생성.
        Member member = memberRepository.findByRefreshToken(refreshToken).orElseThrow(
                () -> new UsernameNotFoundException("유효하지 않은 Refresh Token입니다.")
        );

        Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(refreshToken);

        if (!claims.getBody().getExpiration().before(new Date())) {
            accessToken = recreationAccessToken(claims.getBody().get("roles"));
            logger.info("accessToken" + accessToken);
        }

        Map<String, String> accessTokenResponseMap = new HashMap<>();

        // === 현재시간과 Refresh Token 만료날짜를 통해 남은 만료기간 계산 === //
        long refreshExpireTime = claims.getBody().getExpiration().getTime();
        long diffMin  = (refreshExpireTime - now) / 1000 / 60;

        if(diffMin < 5){
            String newRefreshToken = jwtTokenProvider.generateRefreshToken();
            accessTokenResponseMap.put(REFRESH_TOKEN_HEADER, newRefreshToken);
            //member.updateRefreshToken(newRefreshToken);
            redisDao.setValues(member.getUserName(), newRefreshToken, Duration.ofDays(14));
        }
        accessTokenResponseMap.put(ACCESS_TOKEN_HEADER, accessToken);
        return accessTokenResponseMap;
    }

    public String recreationAccessToken(Object roles){
        Claims claims = Jwts.claims(); // JWT payload 에 저장되는 정보단위
        claims.put("roles", roles); // 정보는 key / value 쌍으로 저장된다.
        Date now = new Date();

        //Access Token
        String accessToken = Jwts.builder()
                .setClaims(claims) // 정보 저장
                .setIssuedAt(now) // 토큰 발행 시간 정보
                .setExpiration(new Date(now.getTime() + ACCESS_TOKEN_EXP_TIME)) // set Expire Time
                .signWith(SignatureAlgorithm.HS256, secretKey)  // 사용할 암호화 알고리즘과
                // signature 에 들어갈 secret값 세팅
                .compact();

        return accessToken;
    }

}
