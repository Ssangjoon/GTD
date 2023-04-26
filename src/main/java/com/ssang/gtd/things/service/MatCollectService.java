package com.ssang.gtd.things.service;

import com.ssang.gtd.things.dto.matcol.MatColCreateDto.MatColCreateRequest;
import com.ssang.gtd.things.dto.matcol.MatColDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface MatCollectService {
    public List<MatColDto> list();
    public MatColDto get(int id);
    public int post(MatColCreateRequest dto, List<MultipartFile> files) throws Exception;
    public int put(MatColDto dto);
    public int delete(int id);
}
