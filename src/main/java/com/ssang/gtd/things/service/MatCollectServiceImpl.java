package com.ssang.gtd.things.service;

import com.ssang.gtd.entity.Collect;
import com.ssang.gtd.things.dao.CollectDao;
import com.ssang.gtd.things.dao.CollectRepository;
import com.ssang.gtd.things.dao.MatCollectDao;
import com.ssang.gtd.things.dao.MatCollectRepository;
import com.ssang.gtd.things.dto.matcol.MatColCreateDto.MatColCreateRequest;
import com.ssang.gtd.things.dto.matcol.MatColDto;
import com.ssang.gtd.utils.file.FileServiceImpl;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MatCollectServiceImpl implements MatCollectService {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final MatCollectDao matCollectDao;
    private final CollectDao collectDao;
    private final FileServiceImpl fileService;
    private final CollectService collectService;
    private final MatCollectRepository matCollectRepository;
    private final CollectRepository collectRepository;


    @Override
    public List<MatColDto> list() { return matCollectDao.list(); }
    @Override
    public MatColDto get(int id) { return matCollectDao.get(id); }

    @Override
    @Transactional(noRollbackFor=Exception.class)
    public int post(MatColCreateRequest dto, List<MultipartFile> files) throws Exception {
        Collect collect = dto.getCollect();
        Collect oldCollect = collectRepository.findById(collect.getId()).orElseThrow(() -> new Exception("존재하지 않는 글"));
        if(dto.getMember().getId().equals(oldCollect.getMember().getId())){
            if(!StringUtils.hasText(collect.getType())){
                Collect newCollect= Collect.builder()
                        .id(collect.getId())
                        .content(collect.getContent())
                        .type("collection")
                        .build();
                dto.setCollect(newCollect);
            }
            oldCollect.update(dto.getCollect().getContent(), dto.getCollect().getType());
        }else{
            throw new Exception("작성자가 아닙니다.");
        }
        matCollectRepository.save(dto.toEntity());
        /*if(files != null && !files.isEmpty()){
            logger.trace("파일 업로드");
            List<Map<String, Object>> params = fileService.fileUpload("material", files, mDto.getMcNo());
            matCollectDao.saveFile(params);
        }*/
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
