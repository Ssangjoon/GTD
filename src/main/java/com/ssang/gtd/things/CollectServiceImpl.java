package com.ssang.gtd.things;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CollectServiceImpl implements CollectService {

    private final CollectDao collectDao;

    public CollectServiceImpl(CollectDao collectDao) {
        this.collectDao = collectDao;
    }
    @Override
    public List<CollectionDto> list() {
        System.out.println(collectDao.list());
        return collectDao.list();
    }
    @Override
    public CollectionDto get(int id) {
        return collectDao.get(id);
    }

    @Override
    public int post(String content) {
        return collectDao.post(content);
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
