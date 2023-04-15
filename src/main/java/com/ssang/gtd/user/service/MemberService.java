package com.ssang.gtd.user.service;

import com.ssang.gtd.user.dto.MemberDto;
import com.ssang.gtd.utils.VO.TokenInfoVO;

import java.util.List;

public interface MemberService {
    List<MemberDto> list();
    MemberDto get(int id);
    int sessionPost(MemberDto dto);
    int post(MemberDto dto) throws Exception;
    int put(MemberDto dto);
    int delete(MemberDto dto);

    MemberDto sessionLogin(MemberDto dto);
    TokenInfoVO login(MemberDto dto);
}
