package com.ssang.gtd.domain.things.service;

import com.ssang.gtd.domain.things.domain.MatCol;
import com.ssang.gtd.domain.things.dto.matcol.MatColCreateDto.MatColServiceDto;
import com.ssang.gtd.domain.things.dto.matcol.MatColDto;
import com.ssang.gtd.domain.things.dto.matcol.MatColGetDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface MatCollectService {
    public Object list();
    public MatColGetDto.MatColGetResponse get(Long id);
    public MatCol post(MatColServiceDto dto, List<MultipartFile> files) throws Exception;
    public int put(MatColDto dto);
    public int delete(int id);
}
