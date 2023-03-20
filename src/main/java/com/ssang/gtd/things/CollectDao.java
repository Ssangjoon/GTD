package com.ssang.gtd.things;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CollectDao {
        List<CollectionDto> list();
        CollectionDto get(int id);
        int post(String content);
        int put(CollectionDto dto);
        int delete(int id);
}
