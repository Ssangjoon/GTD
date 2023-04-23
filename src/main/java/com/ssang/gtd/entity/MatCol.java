package com.ssang.gtd.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import java.util.Date;

@DynamicInsert
@Entity
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MatCol extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(length = 255)
    String goal;
    @Column(length = 50, nullable = false)
    String content;
    @Temporal(TemporalType.DATE)
    Date goalDt;

    @ManyToOne
    @JoinColumn(name="userId")
    Member member;

    @OneToOne
    @JoinColumn(name = "collectId")
    Collect collect;
    @Builder
    public MatCol(Long id, String goal, String content, Date goalDt, Member member, Collect collect) {
        this.id = id;
        this.goal = goal;
        this.content = content;
        this.goalDt = goalDt;
        this.member = member;
        this.collect = collect;
    }
}
