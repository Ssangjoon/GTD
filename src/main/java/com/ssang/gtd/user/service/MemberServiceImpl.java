package com.ssang.gtd.user.service;

import com.ssang.gtd.Role;
import com.ssang.gtd.entity.Member;
import com.ssang.gtd.exception.CustomException;
import com.ssang.gtd.exception.ErrorCode;
import com.ssang.gtd.jwt.TokenProvider;
import com.ssang.gtd.user.dao.MemberDao;
import com.ssang.gtd.user.dao.MemberRepository;
import com.ssang.gtd.user.dto.member.MemberServiceDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;



@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final MemberDao memberDao;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository;
    private final RedisTemplate redisTemplate;


    @Override
    public List<Member> list() {
        return memberRepository.findAll();
    }

    @Override
    public Optional<Member> get(Long id) {
        return memberRepository.findById(id);
    }

    public Member post(MemberServiceDto dto)throws Exception {
        if(memberRepository.findByUserName(dto.getUserName()).isPresent()){
            throw new CustomException(ErrorCode.ALREADY_USER);
        }
        dto.setPassword(passwordEncoder.encode(dto.getPassword()));
        dto.setRole(Role.USER);
        return memberRepository.save(dto.toEntity());
    }

    @Override
    @Transactional
    public Member put(MemberServiceDto dto)throws Exception {
        Member member = memberRepository.findByUserName(dto.getUserName()).orElseThrow(() -> new Exception("존재하지 않는 회원"));
        //member.update(dto.getUserName(), dto.getName(), dto.getEmail());
        return member;
    }

    @Override
    public void delete(Long id) {
        memberRepository.deleteById(id);
    }

    @Override
    public void logout(HttpServletRequest request){
        String accessToken = request.getHeader("Authorization").substring(7);
        // 로그아웃 하고 싶은 토큰이 유효한 지 먼저 검증하기
        if (!jwtTokenProvider.validateToken(accessToken)){
            throw new IllegalArgumentException("로그아웃 : 유효하지 않은 토큰입니다.");
        }

        // Access Token에서 User email을 가져온다
        Authentication authentication = jwtTokenProvider.getAuthentication(accessToken);
        logger.info("authentication" + authentication.toString());
        // Redis에서 해당 User email로 저장된 Refresh Token 이 있는지 여부를 확인 후에 있을 경우 삭제를 한다.
        if (redisTemplate.opsForValue().get("RT:"+authentication.getName())!=null){
            // Refresh Token을 삭제
            redisTemplate.delete("RT:"+authentication.getName());
        }

        // 해당 Access Token 유효시간을 가지고 와서 BlackList에 저장하기
        Long expiration = jwtTokenProvider.getExpiration(accessToken);
        redisTemplate.opsForValue().set(accessToken,"logout",expiration, TimeUnit.MILLISECONDS);
    }

}
