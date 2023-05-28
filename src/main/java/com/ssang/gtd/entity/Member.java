package com.ssang.gtd.entity;


import com.ssang.gtd.oauth2.Role;
import com.ssang.gtd.oauth2.SocialType;
import com.ssang.gtd.test.MemberStatus;
import com.ssang.gtd.test.Gender;
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
    private String userName;
    @Column(length = 50, nullable = false)
    private  String name;
    @Column(length = 100, nullable = false)
    private String password;
    @Column(length = 50, nullable = false)
    private String email;
    @Column(length = 50, nullable = false)
    //@Enumerated(EnumType.STRING)
    private Role role;
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private MemberStatus status;

    @Column(name = "gender", nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Column(length = 50, nullable = true)
    @Enumerated(EnumType.STRING)
    private SocialType socialType; // KAKAO, NAVER, GOOGLE
    @Column(length = 50, nullable = true)
    private String socialId; // 로그인한 소셜 타입의 식별자 값 (일반 로그인인 경우 null)
    @Column(length = 255, nullable = true)
    private String refreshToken; // 리프레시 토큰
    @Column(length = 50, nullable = true)
    private String imageUrl;



    public void update(String userName, String name, String email) {
        this.userName = userName;
        this.name = name;
        this.email = email;
    }
    public void updateRefreshToken(String newToken) {
        this.refreshToken = newToken;
    }

    @Builder
    public Member(Long id, String userName, String name, String password, String email, Role role, MemberStatus status, Gender gender, SocialType socialType, String socialId, String refreshToken, String imageUrl) {
        this.id = id;
        this.userName = userName;
        this.name = name;
        this.password = password;
        this.email = email;
        this.role = role;
        this.status = status;
        this.gender = gender;
        this.socialType = socialType;
        this.socialId = socialId;
        this.refreshToken = refreshToken;
        this.imageUrl = imageUrl;
    }



    /*public String getAuthority() {
        return this.role.getAuthority();
    }*/

    public Member update(String name,String refreshToken) {
        this.name = name;
        this.refreshToken = refreshToken;
        return this;
    }

}
