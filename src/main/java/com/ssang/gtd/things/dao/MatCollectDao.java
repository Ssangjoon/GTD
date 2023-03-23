package com.ssang.gtd.things.dao;


import com.ssang.gtd.things.dto.CollectionDto;
import com.ssang.gtd.things.dto.MatColDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MatCollectDao {
    List<MatColDto> list();
    MatColDto get(int id);
    int post(CollectionDto cDto, MatColDto mdto);
    int put(MatColDto dto);
    int delete(int id);
}
