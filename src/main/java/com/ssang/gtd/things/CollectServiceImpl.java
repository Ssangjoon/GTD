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
    public String test() {
        return "hey";
    }
    @Override
    public List<CollectionDto> list() {
        return collectDao.list();
    }
}
