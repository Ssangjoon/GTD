package com.ssang.gtd.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
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
    Long id;
    @Column(length = 255)
    String content;
    @Column(length = 50, nullable = false)
    @ColumnDefault("'collection'")
    String type;
    @ManyToOne
    @JoinColumn(name="userId")
    Member member;

    @Builder
    public Collect(Long id, String content, String type, Member member) {
        this.id = id;
        this.content = content;
        this.type = type;
        this.member = member;
    }
    public void update(String content, String type) {
        this.content = content;
        this.type = type;
    }

}
