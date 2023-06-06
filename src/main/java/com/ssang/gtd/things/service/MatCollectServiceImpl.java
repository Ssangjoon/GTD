package com.ssang.gtd.things.service;

import com.ssang.gtd.entity.*;
import com.ssang.gtd.exception.ErrorCode;
import com.ssang.gtd.exception.CustomException;
import com.ssang.gtd.things.dao.CollectDao;
import com.ssang.gtd.things.dao.CollectRepository;
import com.ssang.gtd.things.dao.MatCollectDao;
import com.ssang.gtd.things.dao.MatCollectRepository;
import com.ssang.gtd.things.dto.matcol.MatColCreateDto.MatColServiceDto;
import com.ssang.gtd.things.dto.matcol.MatColDto;
import com.ssang.gtd.utils.file.FileRepository;
import com.ssang.gtd.utils.file.FileServiceImpl;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.ssang.gtd.utils.enums.BoardType.MAT_COLLECTION;

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
    private final FileRepository fileRepository;


    @Override
    public List<MatColDto> list() { return matCollectDao.list(); }
    @Override
    public MatColDto get(int id) { return matCollectDao.get(id); }


    @Transactional(noRollbackFor=Exception.class)
    public int post(MatColServiceDto dto, List<MultipartFile> files) throws Exception {

        Collect collect = dto.getCollect();
        MemberSocial member = dto.getMember();
        Collect oldCollect = collectRepository.findById(collect.getId()).orElseThrow(() -> new CustomException(ErrorCode.CAN_NOT_FOUND_BY_ID));

        if(member.getId().equals(oldCollect.getMember().getId())){

            if(!StringUtils.hasText(String.valueOf(collect.getType()))){
                // type 미기재시 update 전에 디폴트 타입 'material'으로 새 객체 생성
                Collect newCollect = Collect.builder()
                        .id(collect.getId())
                        .content(collect.getContent())
                        .member(member)
                        .type(MAT_COLLECTION)
                        .build();

                dto = MatColServiceDto.initMatColCreateRequest(dto,newCollect);
            }

            oldCollect.update(dto.getCollect().getContent(), dto.getCollect().getType());

        }else{
            throw new CustomException(ErrorCode.DIFFRENT_WRITER);
        }

        MatCol savedMatCol = matCollectRepository.save(dto.toEntity());

        if(files != null && !files.isEmpty()){
            logger.trace("파일 업로드");
            // file dto 리스트를 만든다.
            List<FileEntity> params = fileService.fileUpload("material", files, savedMatCol.getId());
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
