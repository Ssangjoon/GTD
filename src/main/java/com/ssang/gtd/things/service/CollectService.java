package com.ssang.gtd.things.service;

import com.ssang.gtd.entity.Collect;
import com.ssang.gtd.things.dto.collect.CollectCreateDto.CollectCreateRequest;
import com.ssang.gtd.things.dto.collect.CollectionUpdateDto.CollectUpdateRequest;

import java.util.List;
import java.util.Optional;

public interface CollectService {
    public List<Collect> list();
    public Optional<Collect> get(Long id);
    public Collect post(CollectCreateRequest dto);
    public Collect put(CollectUpdateRequest dto) throws Exception;
    public void delete(Long id);
}
