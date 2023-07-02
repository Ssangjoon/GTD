package com.ssang.gtd.domain.things.dao.search;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssang.gtd.domain.things.domain.QCollect;
import com.ssang.gtd.domain.things.domain.QFileEntity;
import com.ssang.gtd.domain.things.domain.QMatCol;
import com.ssang.gtd.domain.things.dto.matcol.MatColGetDto;
import com.ssang.gtd.domain.user.domain.QMember;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class MatColRepositoryCustomImpl implements MatColRepositoryCustom{
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final JPAQueryFactory queryFactory;
    QMatCol matCol = QMatCol.matCol;
    QMember member = QMember.member;
    QCollect collect = QCollect.collect;

    public MatColRepositoryCustomImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }
    @Override
    public MatColGetDto.MatColGetResponse search(Long id) {
        return queryFactory
                .select(Projections.bean(MatColGetDto.MatColGetResponse.class,
                        QMatCol.matCol.id
                        , matCol.goal
                        , matCol.content
                        , matCol.goalDt
                        , collect.id.as("collectId")
                        , collect.content.as("collectContent")
                        , collect.type.as("type")
                        , collect.modifiedDate.as("modifiedDate")
                        , collect.createDate.as("createdDate")
                        , member.id.as("memberId")
                        , member.userName.as("userName")
                        , member.name.as("name")
                        , member.gender.as("gender")
                        , QFileEntity.fileEntity
                ))
                .from(matCol)
                .leftJoin(QFileEntity.fileEntity)
                .on(matCol.id.eq(QFileEntity.fileEntity.matcol.id))
                .leftJoin(collect)
                .on(collect.id.eq(matCol.collect.id))
                .leftJoin(member)
                .on(collect.member.id.eq(QMember.member.id))
                .where(matCol.id.eq(id))
                .fetchOne();
    }

    @Override
    public List<MatColGetDto.MatColGetResponse> searchList() {
            return queryFactory
                .select(Projections.bean(MatColGetDto.MatColGetResponse.class,
                        QMatCol.matCol.id
                        , matCol.goal
                        , matCol.content
                        , matCol.goalDt
                        , collect.id.as("collectId")
                        , collect.content.as("collectContent")
                        , collect.type.as("type")
                        , collect.modifiedDate.as("modifiedDate")
                        , collect.createDate.as("createdDate")
                        , member.id.as("memberId")
                        , member.userName.as("userName")
                        , member.name.as("name")
                        , member.gender.as("gender")
                        , QFileEntity.fileEntity
                ))
                .from(matCol)
                .leftJoin(QFileEntity.fileEntity)
                .on(matCol.id.eq(QFileEntity.fileEntity.matcol.id))
                .leftJoin(collect)
                .on(collect.id.eq(matCol.collect.id))
                .leftJoin(member)
                .on(collect.member.id.eq(QMember.member.id))
                .fetch();
    }
}
