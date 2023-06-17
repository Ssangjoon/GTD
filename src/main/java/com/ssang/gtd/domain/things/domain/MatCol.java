package com.ssang.gtd.domain.things.domain;

import com.ssang.gtd.domain.BaseEntity;
import com.ssang.gtd.domain.user.domain.Member;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "collectId")
    Collect collect;
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name="process_step", joinColumns = @JoinColumn(name="matcol_id"))
    private List<String> steps = new ArrayList<>();

    @Builder
    public MatCol(Long id, String goal, String content, LocalDate goalDt, Member member, Collect collect, List<String> steps) {
        this.id = id;
        this.goal = goal;
        this.content = content;
        this.goalDt = goalDt;
        this.collect = collect;
        this.steps = steps;
    }
}
