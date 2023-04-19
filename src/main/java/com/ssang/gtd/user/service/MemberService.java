package com.ssang.gtd.user.service;

import com.ssang.gtd.user.domain.Member;
import com.ssang.gtd.user.dto.MemberCreateDto.MemberCreateRequest;
import com.ssang.gtd.user.dto.MemberDto;
import com.ssang.gtd.utils.TokenInfoVO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface MemberService {
    List<MemberDto> list();
    MemberDto get(int id);
    Member post(MemberCreateRequest dto) throws Exception;
    int put(MemberDto dto);
    int delete(MemberDto dto);
    void logout(HttpServletRequest request);
    ResponseEntity<TokenInfoVO> login(MemberDto dto);
}
