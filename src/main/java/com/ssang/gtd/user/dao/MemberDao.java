package com.ssang.gtd.user.dao;

import com.ssang.gtd.user.dto.member.MemberDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface MemberDao {
    List<MemberDto> list();
    MemberDto get(int id);
    MemberDto getByIdAndPassword(MemberDto dto);
    Optional<MemberDto> getById(String username);
    int post(MemberDto dto);
    int put(MemberDto dto);
    int delete(MemberDto dto);
}
