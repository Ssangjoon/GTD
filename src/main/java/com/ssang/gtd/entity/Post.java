package com.ssang.gtd.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

@DynamicInsert
@Entity
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String content;

    @Builder
    public Post(Long id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }
}
