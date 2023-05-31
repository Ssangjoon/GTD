package com.ssang.gtd.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private Long id;
    @Column(length = 255)
    private String content;
    @Column(length = 50, nullable = false)
    @ColumnDefault(value = "'collection'")
    private String type;

    @Builder
    public Collect(Long id, String content, String type, Member member) {
        this.id = id;
        this.content = content;
        this.type = type;
        this.member = member;
    }
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="userId", nullable = false)
    private Member member;

    public void update(String content, String type) {
        this.content = content;
        this.type = type;
    }

    public enum BoardType {
        COLLECTION("collection","해결을 요구하는 모든 일들을 모은다.")
        ,MAT_COLLECTION("matCollection","목표와 구체적 행동을 기재하여 구체화 시킨다.")
        ,IMMEDIATELY("immediately","즉시 실행 및 해결이 가능한 일들.")
        ,PROJECT("project","해결을 위해 여러 단계가 필요한 일들.")
        ,CALENDER("calender","특정한 날짜에만 해결 가능한 일들")
        ,DELEGATE("delegate","내가 할 수 없는 일들")
        ,NEXT_LIST("next_list","가능한 빠른 해결을 요구하는 일들")
        ,MAYBE("maybe","당장 취할 액션이 없거나 지금 할일이 아니거나")
        ,TRASH("trash","어디에도 속하지 않는 목록들")
        ;

        BoardType(String type, String info) {
            this.type = type;
            this.info = info;
        }

        private String type;
        private String info;
    }

}
