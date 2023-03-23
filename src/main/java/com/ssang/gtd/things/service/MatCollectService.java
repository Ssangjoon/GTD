package com.ssang.gtd.things.service;

import com.ssang.gtd.things.dto.CollectionDto;
import com.ssang.gtd.things.dto.MatColDto;

import java.util.List;

public interface MatCollectService {
    List<MatColDto> list();
    MatColDto get(int id);
    int post(CollectionDto cDto, MatColDto mdto);
    int put(MatColDto dto);
    int delete(int id);
}
