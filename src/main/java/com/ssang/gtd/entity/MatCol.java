package com.ssang.gtd.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Getter
@ToString(exclude = {"member","collect"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MatCol extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 255)
    private String goal;
    @Column(length = 50, nullable = false)
    private String content;
    @Temporal(TemporalType.DATE)
    private Date goalDt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="userId")
    private Member member;

    @OneToOne(fetch = FetchType.LAZY)
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
