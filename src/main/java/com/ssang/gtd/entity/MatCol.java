package com.ssang.gtd.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@ToString
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
    private LocalDate goalDt;
//    @JsonIgnore
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name="userId")
//    private Member member;
    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "collectId")
    Collect collect;
    @Builder
    public MatCol(Long id, String goal, String content, LocalDate goalDt, Member member, Collect collect) {
        this.id = id;
        this.goal = goal;
        this.content = content;
        this.goalDt = goalDt;
        this.collect = collect;
    }
}
