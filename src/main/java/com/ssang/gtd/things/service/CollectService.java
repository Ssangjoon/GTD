package com.ssang.gtd.things.service;

import com.ssang.gtd.entity.Collect;
import com.ssang.gtd.things.dto.collect.CollectCreateDto.CollectCreateRequest;
import com.ssang.gtd.things.dto.collect.CollectionUpdateDto.CollectUpdateRequest;

import java.util.List;
import java.util.Optional;

public interface CollectService {
    List<Collect> list();
    Optional<Collect> get(Long id);
    Collect post(CollectCreateRequest dto);
    Collect put(CollectUpdateRequest dto) throws Exception;
    void delete(Long id);
}
