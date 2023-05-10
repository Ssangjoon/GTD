package com.ssang.gtd.user.service;

import com.ssang.gtd.Role;
import com.ssang.gtd.entity.Member;
import com.ssang.gtd.exception.CustomException;
import com.ssang.gtd.exception.ErrorCode;
import com.ssang.gtd.jwt.TokenProvider;
import com.ssang.gtd.redis.RedisDao;
import com.ssang.gtd.user.dao.MemberDao;
import com.ssang.gtd.user.dao.MemberRepository;
import com.ssang.gtd.user.dto.MemberServiceDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

import static com.ssang.gtd.jwt.JwtConstants.TOKEN_HEADER_PREFIX;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;


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
    private final RedisDao redisDao;


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
    public ResponseEntity<HttpStatus> logout(HttpServletRequest request){
        String token = request.getHeader(AUTHORIZATION).substring(TOKEN_HEADER_PREFIX.length());

        Long expiration = jwtTokenProvider.getExpiration(token);
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        // redis에서 해당 이름으로 저장된 Refresh Token 이 있는지 여부를 확인 후에 있을 경우 삭제를 한다.
        if(redisDao.getValues(username) != null){
            redisDao.deleteValues(username);
            logger.info("Redis Refresh Token 삭제 완료 ===>  username : {} , expiration : {}", username, expiration);
        }

        redisDao.setValues(token,"logout", Duration.ofMillis(expiration));
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
