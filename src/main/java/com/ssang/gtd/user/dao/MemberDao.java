package com.ssang.gtd.user.dao;

import com.ssang.gtd.user.dto.MemberDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MemberDao {
    List<MemberDto> list();
    MemberDto get(int id);
    int post(MemberDto dto);
    int put(MemberDto dto);
    int delete(int id);
}
