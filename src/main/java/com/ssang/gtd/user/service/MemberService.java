package com.ssang.gtd.user.service;

import com.ssang.gtd.user.dto.MemberDto;

import java.util.List;

public interface MemberService {
    List<MemberDto> list();
    MemberDto get(int id);
    int post(MemberDto dto);
    int put(MemberDto dto);
    int delete(int id);
}
