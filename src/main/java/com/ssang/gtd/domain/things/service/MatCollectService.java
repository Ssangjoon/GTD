package com.ssang.gtd.domain.things.service;

import com.ssang.gtd.domain.things.dto.matcol.MatColCreateDto.MatColServiceDto;
import com.ssang.gtd.domain.things.dto.matcol.MatColDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface MatCollectService {
    public List<MatColDto> list();
    public MatColDto get(int id);
    public int post(MatColServiceDto dto, List<MultipartFile> files) throws Exception;
    public int put(MatColDto dto);
    public int delete(int id);
}
