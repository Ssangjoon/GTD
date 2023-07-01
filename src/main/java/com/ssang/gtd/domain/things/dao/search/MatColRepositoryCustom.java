package com.ssang.gtd.domain.things.dao.search;

import com.ssang.gtd.domain.things.dto.matcol.MatColGetDto;

import java.util.List;

public interface MatColRepositoryCustom {
    public MatColGetDto.MatColGetResponse search(Long id);
    public List<MatColGetDto.MatColGetResponse> searchList();
}
