package com.ssang.gtd.domain.things.dao;


import com.ssang.gtd.domain.things.dto.matcol.MatColDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface MatCollectDao {
    List<MatColDto> list();
    MatColDto get(int id);
    int post(MatColDto mdto);
    int put(MatColDto dto);
    int delete(int id);
    int saveFile(List<Map<String, Object>> params);
}
