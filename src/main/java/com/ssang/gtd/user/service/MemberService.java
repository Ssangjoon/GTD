package com.ssang.gtd.user.service;

import com.ssang.gtd.user.domain.Member;
import com.ssang.gtd.user.dto.MemberCreateDto.MemberCreateRequest;
import com.ssang.gtd.user.dto.MemberDto;
import com.ssang.gtd.user.dto.MemberUpdateDto.MemberUpdateRequest;
import com.ssang.gtd.utils.TokenInfoVO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface MemberService {
    List<MemberDto> list();
    MemberDto get(int id);
    Member post(MemberCreateRequest dto) throws Exception;
    Member put(MemberUpdateRequest dto) throws Exception;
    int delete(MemberDto dto);
    void logout(HttpServletRequest request);
    ResponseEntity<TokenInfoVO> login(MemberDto dto);
}
