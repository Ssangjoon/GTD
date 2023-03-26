package com.ssang.gtd.things.service;

import com.ssang.gtd.things.dao.CollectDao;
import com.ssang.gtd.things.dao.MatCollectDao;
import com.ssang.gtd.things.dto.CollectionDto;
import com.ssang.gtd.things.dto.MatColDto;
import com.ssang.gtd.utils.file.FileDto;
import com.ssang.gtd.utils.file.FileServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@Service
public class MatCollectServiceImpl implements MatCollectService {
    private static final Logger logger = LoggerFactory.getLogger(MatCollectServiceImpl.class);
    private final MatCollectDao matCollectDao;
    private final CollectDao collectDao;
    private final FileServiceImpl fileService;

    public MatCollectServiceImpl(MatCollectDao matCollectDao, CollectDao collectDao, FileServiceImpl fileService) {
        this.matCollectDao = matCollectDao;
        this.collectDao = collectDao;
        this.fileService = fileService;
    }

    @Override
    public List<MatColDto> list() { return matCollectDao.list(); }
    @Override
    public MatColDto get(int id) { return matCollectDao.get(id); }

    @Override
    @Transactional
    public int post(CollectionDto cDto, MatColDto mDto, List<MultipartFile> files) throws Exception {
        collectDao.put(cDto);
        matCollectDao.post(mDto);
        if(files != null && !files.isEmpty()){
            logger.trace("파일 업로드");
            List<Map<String, Object>> params = fileService.fileUpload("Gallery", files, mDto.getMcNo());
            matCollectDao.saveFile(params);
        }
        return 1;
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
