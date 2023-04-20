package com.ssang.gtd.things.service;

import com.ssang.gtd.things.dao.CollectDao;
import com.ssang.gtd.things.dao.CollectRepository;
import com.ssang.gtd.things.domain.Collect;
import com.ssang.gtd.things.dto.collect.CollectCreateDto.CollectCreateRequest;
import com.ssang.gtd.things.dto.collect.CollectionDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CollectServiceImpl implements CollectService {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final CollectDao collectDao;
    private final CollectRepository collectRepository;

    @Override
    public List<CollectionDto> list() { return collectDao.list(); }
    @Override
    public CollectionDto get(int id) { return collectDao.get(id); }
    @Override
    public Collect post(CollectCreateRequest dto) {
        return collectRepository.save(dto.toEntity());
    }
    @Override
    public int put(CollectionDto dto) {
        return collectDao.put(dto);
    }
    @Override
    public int delete(int id) {
        return collectDao.delete(id);
    }
}
