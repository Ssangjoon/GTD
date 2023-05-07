package com.ssang.gtd.jwt;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.ssang.gtd.entity.Member;
import com.ssang.gtd.exception.CustomException;
import com.ssang.gtd.exception.ErrorCode;
import com.ssang.gtd.user.dao.MemberRepository;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

import static com.ssang.gtd.jwt.JwtConstants.*;

@Service
@Getter
@RequiredArgsConstructor
public class JwtService {
    private final Logger log = LoggerFactory.getLogger(getClass());
    @Value("${jwt.secret}")
    private String secretKey;
    private final MemberRepository memberRepository;

    @Transactional
    public Member getMemberByRefreshToken(String token){
        return memberRepository.findByRefreshToken(token)
                .orElseThrow(() -> new CustomException(ErrorCode.JWT_REFRESH_EXPRIRED));
    }
    @Transactional
    public void setRefreshToken(String username,String refreshJwt){
        memberRepository.findByUserName(username)
                .ifPresent(member -> member.updateRefreshToken(refreshJwt));
    }
    @Transactional
    public void removeRefreshToken(String token){
        memberRepository.findByRefreshToken(token)
                .ifPresent(t -> t.updateRefreshToken(null));
    }
    public void logout(HttpServletRequest request){
        try{
            checkHeaderValid(request);
            String refreshJwtToken = request
                    .getHeader(RT_HEADER)
                    .replace(TOKEN_HEADER_PREFIX, "");
            removeRefreshToken(refreshJwtToken);
        } catch (Exception e){
            throw new CustomException(ErrorCode.JWT_REFRESH_NOT_VALID);
        }
    }
    public void checkHeaderValid(HttpServletRequest request){
        String accessJwt = request.getHeader(AT_HEADER);
        String refreshJwt = request.getHeader(RT_HEADER);
        if(accessJwt == null){
            throw new CustomException(ErrorCode.JWT_ACCESS_NOT_VALID);
        } else if(refreshJwt == null){
            throw new CustomException(ErrorCode.JWT_REFRESH_NOT_VALID);
        }
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
