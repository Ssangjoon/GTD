package com.ssang.gtd.domain.things.domain;

import com.ssang.gtd.domain.BaseEntity;
import com.ssang.gtd.domain.user.domain.MemberSocial;
import com.ssang.gtd.global.enums.BoardType;
import com.ssang.gtd.global.enums.BoardTypeConverter;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@DynamicInsert
@DynamicUpdate
@Entity
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Collect extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 255, nullable = false)
    private String content;

    @Column(length = 50, nullable = false)
    @Convert(converter = BoardTypeConverter.class)
    private BoardType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="userId", nullable = false)
    private MemberSocial member;

    @Builder
    public Collect(Long id, String content, BoardType type, MemberSocial member) {
        this.id = id;
        this.content = content;
        this.type = type;
        this.member = member;
    }

    public void update(String content, BoardType type) {
        this.content = content;
        this.type = type;
    }


}
