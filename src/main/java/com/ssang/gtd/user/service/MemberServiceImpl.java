package com.ssang.gtd.user.service;

import com.ssang.gtd.entity.MemberDetail;
import com.ssang.gtd.entity.MemberSocial;
import com.ssang.gtd.exception.CustomException;
import com.ssang.gtd.exception.ErrorCode;
import com.ssang.gtd.jwt.TokenProvider;
import com.ssang.gtd.redis.RedisDao;
import com.ssang.gtd.user.dao.MemberDetialRepository;
import com.ssang.gtd.user.dao.MemberRepository;
import com.ssang.gtd.user.dao.MemberSocialTypeRepository;
import com.ssang.gtd.user.dto.member.MemberServiceDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository;
    private final MemberSocialTypeRepository memberSocialTypeRepository;
    private final MemberDetialRepository memberDetialRepository;
    private final RedisDao redisDao;

    @Override
    public List<MemberSocial> list() {
        return memberSocialTypeRepository.findAll();
    }


    @Override
    public Optional<MemberSocial> get(Long id) {

        return memberSocialTypeRepository.findById(id);
    }

    public MemberDetail post(MemberServiceDto dto)throws Exception {
        String userName = dto.getUserName();
        String email = dto.getEmail();

        // TODO : email, username 중복 검사, 비밀번호 검사
        if (memberSocialTypeRepository.existsByUserNameOrEmail(userName, email)) {
            throw new CustomException(ErrorCode.ALREADY_USER);
        }

        dto.setPassword(passwordEncoder.encode(dto.getPassword()));
        return memberDetialRepository.save(dto.toMemberEntity());
    }

    @Override
    @Transactional
    public MemberSocial put(MemberServiceDto dto)throws Exception {
        MemberSocial member = memberSocialTypeRepository.findByEmail(dto.getEmail()).orElseThrow(() -> new Exception("존재하지 않는 회원"));
        member.update(dto.getUserName(), dto.getName(), dto.getEmail());
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
