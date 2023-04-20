package com.ssang.gtd.user.service;

import com.ssang.gtd.user.domain.Member;
import com.ssang.gtd.user.dto.MemberCreateDto.MemberCreateRequest;
import com.ssang.gtd.user.dto.MemberDto;
import com.ssang.gtd.user.dto.MemberUpdateDto.MemberUpdateRequest;
import com.ssang.gtd.utils.TokenInfoVO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface MemberService {
    List<Member> list();
    Optional<Member> get(Long id);
    Member post(MemberCreateRequest dto) throws Exception;
    Member put(MemberUpdateRequest dto) throws Exception;
    void delete(Long id);
    void logout(HttpServletRequest request);
    ResponseEntity<TokenInfoVO> login(MemberDto dto);
}
