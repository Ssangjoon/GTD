package com.ssang.gtd.entity;


import com.ssang.gtd.oauth2.Role;
import com.ssang.gtd.test.Gender;
import com.ssang.gtd.test.MemberStatus;
import jakarta.persistence.*;
import lombok.*;
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
    private String email;
    @Column(length = 100, nullable = true)
    private String password;
    @Column(length = 50, nullable = true)
    private String userName;
    @Column(length = 50, nullable = true)
    private  String name;
    @Column(length = 50, nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private MemberStatus status;

    @Column(name = "gender", nullable = true)
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Column(length = 255, nullable = true)
    private String refreshToken; // 리프레시 토큰


    public void update(String userName, String name, String email) {
        this.userName = userName;
        this.name = name;
        this.email = email;
    }

    @Builder
    public Member(Long id, String userName, String name, String password, String email, Role role, MemberStatus status, Gender gender,String refreshToken) {
        this.id = id;
        this.userName = userName;
        this.name = name;
        this.password = password;
        this.email = email;
        this.role = role;
        this.status = status;
        this.gender = gender;
        this.refreshToken = refreshToken;
    }

    public Member update(String name,String refreshToken) {
        this.name = name;
        this.refreshToken = refreshToken;
        return this;
    }

}
