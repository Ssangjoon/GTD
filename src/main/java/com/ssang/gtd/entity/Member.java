package com.ssang.gtd.entity;

import com.ssang.gtd.Role;
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
    //@Enumerated(EnumType.STRING)
    @Column(length = 50, nullable = false)
    private Role role;
    @Column(length = 255, nullable = false)
    private String refreshToken; // 리프레시 토큰



    public void update(String userName, String name, String email) {
        this.userName = userName;
        this.name = name;
        this.email = email;
    }
    public void updateRefreshToken(String newToken) {
        this.refreshToken = newToken;
    }

    @Builder
    public Member(Long id, String userName, String name, String password, String status, String email, Role role, String refreshToken) {
        this.id = id;
        this.userName = userName;
        this.name = name;
        this.password = password;
        this.status = status;
        this.email = email;
        this.role = role;
        this.refreshToken = refreshToken;
    }
    public String getAuthority() {
        return this.role.getAuthority();
    }

    public Member update(String name,String refreshToken) {
        this.name = name;
        this.refreshToken = refreshToken;
        return this;
    }
    // 유저 권한 설정 메소드
    /*public void authorizeUser() {
        this.role = Role.USER;
    }

    public void updateRefreshToken(String updateRefreshToken) {
        this.refreshToken = updateRefreshToken;
    }*/
}
