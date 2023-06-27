package com.ssang.gtd.domain.things.dao.search;

import com.ssang.gtd.domain.things.dto.matcol.MatColFileDto;

public interface MatColRepositoryCustom {
    public MatColFileDto search(Long id);
    public Object searchList();
}
