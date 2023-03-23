package com.ssang.gtd.things.service;

import com.ssang.gtd.things.dto.CollectionDto;

import java.util.List;

public interface CollectService {
    List<CollectionDto> list();
    CollectionDto get(int id);
    int post(CollectionDto dto);
    int put(CollectionDto dto);
    int delete(int id);
}
