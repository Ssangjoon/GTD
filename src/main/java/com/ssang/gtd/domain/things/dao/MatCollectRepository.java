package com.ssang.gtd.domain.things.dao;

import com.ssang.gtd.domain.things.dao.search.MatColRepositoryCustom;
import com.ssang.gtd.domain.things.domain.MatCol;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatCollectRepository extends JpaRepository<MatCol, Long> , MatColRepositoryCustom {

}
