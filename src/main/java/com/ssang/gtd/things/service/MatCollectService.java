package com.ssang.gtd.things.service;

import com.ssang.gtd.things.dto.collect.CollectionDto;
import com.ssang.gtd.things.dto.matcol.MatColDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface MatCollectService {
    List<MatColDto> list();
    MatColDto get(int id);
    int post(CollectionDto cDto, MatColDto mdto, List<MultipartFile> files) throws Exception;
    int put(MatColDto dto);
    int delete(int id);
}
