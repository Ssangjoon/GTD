package com.ssang.gtd.domain.things.dao.search;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssang.gtd.domain.things.domain.QFileEntity;
import com.ssang.gtd.domain.things.domain.QMatCol;
import com.ssang.gtd.domain.things.dto.matcol.MatColFileDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MatColRepositoryCustomImpl implements MatColRepositoryCustom{
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final JPAQueryFactory queryFactory;

    public MatColRepositoryCustomImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }
    @Override
    public MatColFileDto search(Long id) {
        QMatCol matCol = new QMatCol("m");
        QFileEntity fileEntity = new QFileEntity("m");

        return queryFactory
                .select(Projections.bean(MatColFileDto.class,
                        matCol.id
                        ,matCol.goal
                        ,matCol.content
                        ,matCol.collect
                        ,matCol.goal
                        ,fileEntity
                ))
                .from(matCol)
                .leftJoin(fileEntity)
                .on(matCol.id.eq(fileEntity.matcol.id))
                .where(matCol.id.eq(id))
                .fetchOne();
    }

    @Override
    public Object searchList() {
        return null;
    }
}
