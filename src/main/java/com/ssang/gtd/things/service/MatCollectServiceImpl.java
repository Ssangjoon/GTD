package com.ssang.gtd.things.service;

import com.ssang.gtd.things.dao.CollectDao;
import com.ssang.gtd.things.dao.MatCollectDao;
import com.ssang.gtd.things.dto.CollectionDto;
import com.ssang.gtd.things.dto.MatColDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MatCollectServiceImpl implements MatCollectService {
    private static final Logger logger = LogManager.getLogger(MatCollectServiceImpl.class);
    private final MatCollectDao matCollectDao;
    private final CollectDao collectDao;

    public MatCollectServiceImpl(MatCollectDao matCollectDao, CollectDao collectDao) {
        this.matCollectDao = matCollectDao;
        this.collectDao = collectDao;
    }

    @Override
    public List<MatColDto> list() { return matCollectDao.list(); }
    @Override
    public MatColDto get(int id) { return matCollectDao.get(id); }
    @Override
    @Transactional
    public int post(CollectionDto cDto, MatColDto mDto) {
        collectDao.put(cDto);
        int result = matCollectDao.post(mDto);
        return result;
    }
    @Override
    public int put(MatColDto dto) {
        return matCollectDao.put(dto);
    }
    @Override
    public int delete(int id) {
        return matCollectDao.delete(id);
    }
}
