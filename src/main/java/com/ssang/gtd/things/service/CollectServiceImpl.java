package com.ssang.gtd.things.service;

import com.ssang.gtd.things.dao.CollectDao;
import com.ssang.gtd.things.dto.CollectionDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CollectServiceImpl implements CollectService {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final CollectDao collectDao;

    public CollectServiceImpl(CollectDao collectDao) { this.collectDao = collectDao; }
    @Override
    public List<CollectionDto> list() { return collectDao.list(); }
    @Override
    public CollectionDto get(int id) { return collectDao.get(id); }
    @Override
    public int post(CollectionDto dto) {
        return collectDao.post(dto);
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
