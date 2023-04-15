package com.ssang.gtd.user.service;

import com.ssang.gtd.user.dto.MemberDto;
import com.ssang.gtd.utils.TokenInfoVO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface MemberService {
    List<MemberDto> list();
    MemberDto get(int id);
    int post(MemberDto dto) throws Exception;
    int put(MemberDto dto);
    int delete(MemberDto dto);

    ResponseEntity<TokenInfoVO> login(MemberDto dto);
}
