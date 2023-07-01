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

    public MatColRepositoryCustomImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }
    @Override
    public MatColGetDto.MatColGetResponse search(Long id) {
        return queryFactory
                .select(Projections.bean(MatColGetDto.MatColGetResponse.class,
                        QMatCol.matCol.id
                        , QMatCol.matCol.goal
                        , QMatCol.matCol.content
                        , QMatCol.matCol.goalDt
                        , QCollect.collect.id.as("collectId")
                        , QCollect.collect.content.as("collectContent")
                        , QCollect.collect.type.as("type")
                        , QCollect.collect.modifiedDate.as("modifiedDate")
                        , QCollect.collect.createDate.as("createdDate")
                        , QMember.member.id.as("memberId")
                        , QMember.member.userName.as("userName")
                        , QMember.member.name.as("name")
                        , QMember.member.gender.as("gender")
                        , QFileEntity.fileEntity
                ))
                .from(QMatCol.matCol)
                .leftJoin(QFileEntity.fileEntity)
                .on(QMatCol.matCol.id.eq(QFileEntity.fileEntity.matcol.id))
                .leftJoin(QCollect.collect)
                .on(QCollect.collect.id.eq(QMatCol.matCol.collect.id))
                .leftJoin(QMember.member)
                .on(QCollect.collect.member.id.eq(QMember.member.id))
                .where(QMatCol.matCol.id.eq(id))
                .fetchOne();
    }

    @Override
    public List<MatColGetDto.MatColGetResponse> searchList() {
            return queryFactory
                .select(Projections.bean(MatColGetDto.MatColGetResponse.class,
                        QMatCol.matCol.id
                        , QMatCol.matCol.goal
                        , QMatCol.matCol.content
                        , QMatCol.matCol.goalDt
                        , QCollect.collect.id.as("collectId")
                        , QCollect.collect.content.as("collectContent")
                        , QCollect.collect.type.as("type")
                        , QCollect.collect.modifiedDate.as("modifiedDate")
                        , QCollect.collect.createDate.as("createdDate")
                        , QMember.member.id.as("memberId")
                        , QMember.member.userName.as("userName")
                        , QMember.member.name.as("name")
                        , QMember.member.gender.as("gender")
                        , QFileEntity.fileEntity
                ))
                .from(QMatCol.matCol)
                .leftJoin(QFileEntity.fileEntity)
                .on(QMatCol.matCol.id.eq(QFileEntity.fileEntity.matcol.id))
                .leftJoin(QCollect.collect)
                .on(QCollect.collect.id.eq(QMatCol.matCol.collect.id))
                .leftJoin(QMember.member)
                .on(QCollect.collect.member.id.eq(QMember.member.id))
                .fetch();
    }
}
