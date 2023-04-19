package com.ssang.gtd.user.domain;

import com.ssang.gtd.utils.cons.UserRoleEnum;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import java.time.OffsetDateTime;

@DynamicInsert
@Entity
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long uNo;

    @Column(length = 50, nullable = false)
    String id;
    @Column(length = 100, nullable = false)
    String password;
    @Column(length = 50, nullable = false)
    String name;
    @Column(length = 50, nullable = false)
    @ColumnDefault("1")
    String status;
    @Column(length = 50, nullable = false)
    String email;
    @Column(length = 50, nullable = false)
    UserRoleEnum role;

    private OffsetDateTime createdAt = OffsetDateTime.now();
    @Builder
    public Member(String id, String password, String name, String email, UserRoleEnum role) {
        this.id = id;
        this.password = password;
        this.name = name;
        this.email = email;
        this.role = role;
    }
    /*https://testGTD.com*/
}
