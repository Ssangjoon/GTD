package com.ssang.gtd.domain.things.dao.search;

import com.ssang.gtd.domain.things.dto.matcol.MatColGetDto;

public interface MatColRepositoryCustom {
    public MatColGetDto.MatColGetResponse search(Long id);
    public Object searchList();
}
