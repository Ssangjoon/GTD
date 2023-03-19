package com.ssang.gtd.things;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

public interface CollectDao {
        List<CollectionDto> list();
}
