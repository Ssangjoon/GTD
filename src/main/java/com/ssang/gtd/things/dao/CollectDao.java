package com.ssang.gtd.things.dao;

import com.ssang.gtd.things.dto.collect.CollectionDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CollectDao {
        List<CollectionDto> list();
        CollectionDto get(int id);
        int post(CollectionDto dto);
        int put(CollectionDto dto);
        int delete(int id);
}
