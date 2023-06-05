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

    @Embedded
    private SocialGuest socialGuest;

    public void update(String userName, String name, String email) {
        this.userName = userName;
        this.name = name;
        this.email = email;
    }


    @Builder
    public Member(Long id, String email, String password, String userName, String name, Role role, MemberStatus status, Gender gender, SocialGuest socialGuest) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.userName = userName;
        this.name = name;
        this.role = role;
        this.status = status;
        this.gender = gender;
        this.socialGuest = socialGuest;
    }

    public Member update(String name) {
        this.name = name;
        return this;
    }

}
