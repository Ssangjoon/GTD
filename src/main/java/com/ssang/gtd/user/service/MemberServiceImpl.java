package com.ssang.gtd.user.service;

import com.ssang.gtd.filter.JwtAuthenticationFilter;
import com.ssang.gtd.jwt.JwtTokenProvider;
import com.ssang.gtd.user.dao.MemberDao;
import com.ssang.gtd.user.dao.MemberRepository;
import com.ssang.gtd.user.domain.Member;
import com.ssang.gtd.user.dto.MemberCreateDto.MemberCreateRequest;
import com.ssang.gtd.user.dto.MemberDto;
import com.ssang.gtd.utils.TokenInfoVO;
import com.ssang.gtd.utils.cons.UserRoleEnum;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final MemberDao memberDao;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository;


    @Override
    public List<MemberDto> list() {
        return memberDao.list();
    }

    @Override
    public MemberDto get(int id) {
        return memberDao.get(id);
    }

    public Member post(MemberCreateRequest dto)throws Exception {
        if(memberRepository.findById(dto.getId()).isPresent()){
            throw new Exception("이미 존재하는 ID");
        }
        dto.setPassword(passwordEncoder.encode(dto.getPassword()));
        dto.setRole(UserRoleEnum.USER);
        return memberRepository.save(dto.toEntity());
    }

    @Override
    public int put(MemberDto dto) {
        return memberDao.put(dto);
    }

    @Override
    public int delete(MemberDto dto) {
        return memberDao.delete(dto);
    }

    @Override
    public ResponseEntity<TokenInfoVO> login(MemberDto dto) {
        // 1. Login ID/PW 를 기반으로 Authentication 객체 생성
        // 이때 authentication 는 인증 여부를 확인하는 authenticated 값이 false
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(dto.getId(), dto.getPassword());
        // 2. 실제 검증 (사용자 비밀번호 체크)이 이루어지는 부분
        // authenticate 매서드가 실행될 때 CustomUserDetailsService 에서 만든 loadUserByUsername 메서드가 실행
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        TokenInfoVO tokenInfo = jwtTokenProvider.generateToken(authentication);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtAuthenticationFilter.AUTHORIZATION_HEADER, "Bearer " + tokenInfo);
        return new ResponseEntity<>(tokenInfo, httpHeaders, HttpStatus.OK);
    }
}
