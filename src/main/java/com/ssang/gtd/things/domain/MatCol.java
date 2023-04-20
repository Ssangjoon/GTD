package com.ssang.gtd.things.domain;

import com.ssang.gtd.BaseEntity;
import com.ssang.gtd.user.domain.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.DynamicInsert;

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
    @OneToOne
    @JoinColumn(name="userId")
    Member member;

    @OneToOne
    @JoinColumn(name = "collectId")
    private Collect collect;
}
