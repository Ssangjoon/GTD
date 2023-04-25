package com.ssang.gtd.entity;

import com.ssang.gtd.utils.cons.UserRoleEnum;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

@DynamicInsert
@Entity
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 50, nullable = false)
    private String userName;
    @Column(length = 50, nullable = false)
    private  String name;
    @Column(length = 100, nullable = false)
    private String password;
    @Column(length = 50, nullable = false)
    @ColumnDefault("1")
    private String status;
    @Column(length = 50, nullable = false)
    private String email;
    @Column(length = 50, nullable = false)
    private UserRoleEnum role;


    @Builder
    public Member(String userName, String password, String name, String email, UserRoleEnum role) {
        this.userName = userName;
        this.password = password;
        this.name = name;
        this.email = email;
        this.role = role;
    }

    public void update(String userName, String name, String password, String email) {
        this.userName = userName;
        this.name = name;
        this.password = password;
        this.email = email;
    }
    /*https://testGTD.com*/
}