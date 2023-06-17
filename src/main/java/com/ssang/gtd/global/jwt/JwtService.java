package com.ssang.gtd.global.jwt;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.ssang.gtd.domain.user.dao.MemberRepository;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

import static com.ssang.gtd.global.jwt.JwtConstants.ACCESS_TOKEN_HEADER;
import static com.ssang.gtd.global.jwt.JwtConstants.REFRESH_TOKEN_HEADER;

@Service
@Getter
@RequiredArgsConstructor
public class JwtService {
    private final Logger log = LoggerFactory.getLogger(getClass());
    @Value("${jwt.secret}")
    private String secretKey;
    private final MemberRepository memberRepository;


    public void sendAccessAndRefreshToken(HttpServletResponse response, String accessToken, String refreshToken) {
        response.setStatus(HttpServletResponse.SC_OK);
        if(accessToken != null){
            response.setHeader(ACCESS_TOKEN_HEADER, accessToken);
        }
        if(refreshToken != null){
            response.setHeader(REFRESH_TOKEN_HEADER, refreshToken);
        }
        log.info("Access Token, Refresh Token 헤더 설정 완료");
    }
    public boolean isNeedToUpdateRefreshToken(String token){
        try{
            Date expriesAt = Jwts.claims().getExpiration();
            Date current = new Date(System.currentTimeMillis());
            Calendar calender = Calendar.getInstance();
            calender.setTime(current);
            calender.add(Calendar.DATE,7);
            Date after7dayFromToday = calender.getTime();

            if(expriesAt.before(after7dayFromToday)){
                log.info("refreshToken 7일 이내 만료");
                return true;
            }
        }catch (TokenExpiredException e){
            return true;
        }
        return false;
    }
}
