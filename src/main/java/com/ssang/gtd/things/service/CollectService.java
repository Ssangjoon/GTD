package com.ssang.gtd.things.service;

import com.ssang.gtd.entity.Collect;
import com.ssang.gtd.things.dto.collect.CollectServiceDto;

import java.util.List;
import java.util.Optional;

public interface CollectService {
    public List<Collect> list();
    public Optional<Collect> get(Long id);
    public Collect post(CollectServiceDto dto);
    public Collect put(CollectServiceDto dto) throws Exception;
    public void delete(Long id);
}
