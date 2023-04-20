package com.ssang.gtd.things.service;

import com.ssang.gtd.things.domain.Collect;
import com.ssang.gtd.things.dto.collect.CollectCreateDto.CollectCreateRequest;
import com.ssang.gtd.things.dto.collect.CollectionDto;

import java.util.List;

public interface CollectService {
    List<CollectionDto> list();
    CollectionDto get(int id);
    Collect post(CollectCreateRequest dto);
    int put(CollectionDto dto);
    int delete(int id);
}
