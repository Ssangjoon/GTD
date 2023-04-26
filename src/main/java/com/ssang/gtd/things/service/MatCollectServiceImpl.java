package com.ssang.gtd.things.service;

import com.ssang.gtd.entity.Collect;
import com.ssang.gtd.entity.FileEntity;
import com.ssang.gtd.entity.MatCol;
import com.ssang.gtd.things.dao.CollectDao;
import com.ssang.gtd.things.dao.CollectRepository;
import com.ssang.gtd.things.dao.MatCollectDao;
import com.ssang.gtd.things.dao.MatCollectRepository;
import com.ssang.gtd.things.dto.matcol.MatColCreateDto.MatColCreateRequest;
import com.ssang.gtd.things.dto.matcol.MatColDto;
import com.ssang.gtd.utils.file.FileRepository;
import com.ssang.gtd.utils.file.FileServiceImpl;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MatCollectServiceImpl implements MatCollectService {
    @PersistenceContext
    private EntityManager em;
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final MatCollectDao matCollectDao;
    private final CollectDao collectDao;
    private final FileServiceImpl fileService;
    private final CollectService collectService;
    private final MatCollectRepository matCollectRepository;
    private final CollectRepository collectRepository;
    private final FileRepository fileRepository;


    @Override
    public List<MatColDto> list() { return matCollectDao.list(); }
    @Override
    public MatColDto get(int id) { return matCollectDao.get(id); }

    @Override
    //@Transactional(noRollbackFor=Exception.class)
    public int post(MatColCreateRequest dto, List<MultipartFile> files) throws Exception {
        /*EntityTransaction tx = em.getTransaction();
        tx.begin();*/

        Collect collect = dto.getCollect();
        Collect oldCollect = collectRepository.findById(collect.getId()).orElseThrow(() -> new Exception("존재하지 않는 글"));

        if(dto.getMember().getId().equals(oldCollect.getMember().getId())){

            if(!StringUtils.hasText(collect.getType())){
                // type 미기재시 update 전에 디폴트 타입 'collection'으로 새 객체 생성
                Collect newCollect= Collect.builder()
                        .id(collect.getId())
                        .content(collect.getContent())
                        .type("collection")
                        .build();

                //dto.addCollectType(newCollect);
                dto.addCollectTypeTest(dto, newCollect);
                logger.info("dto.getCollect().getType() ======> ");
                logger.info(dto.getCollect().getType());
            }

            oldCollect.update(dto.getCollect().getContent(), dto.getCollect().getType());

        }else{
            throw new Exception("작성자가 아닙니다.");
        }

        MatCol savedMatCol = matCollectRepository.save(dto.toEntity());
        /*em.persist(savedMatCol);
        em.flush();*/

        if(files != null && !files.isEmpty()){
            logger.trace("파일 업로드");
            // file dto 리스트를 만든다.
            List<FileEntity> params = fileService.fileUpload("material", files, savedMatCol.getId());
            logger.info(String.valueOf("matCollectRepository.findById(savedMatCol.getId()))"));
            logger.info("resutl ===>> " + String.valueOf(matCollectRepository.findById(savedMatCol.getId())));
            for(FileEntity param:params){
                logger.info(param.toString());
            }
            fileRepository.saveAll(params);
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
