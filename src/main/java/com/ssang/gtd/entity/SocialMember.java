package com.ssang.gtd.entity;

import com.ssang.gtd.oauth2.Role;
import com.ssang.gtd.oauth2.SocialType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SocialMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String email; // 이메일
    private String password; // 비밀번호
    private String nickname; // 닉네임
    private String imageUrl; // 프로필 이미지
    private int age;
    private String city; // 사는 도시

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private SocialType socialType; // KAKAO, NAVER, GOOGLE

    private String socialId; // 로그인한 소셜 타입의 식별자 값 (일반 로그인인 경우 null)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member")
    private Member member;

    // 유저 권한 설정 메소드
    public void authorizeUser() {
        this.role = Role.USER;
    }

    @Builder
    public SocialMember(Long id, String email, String password, String nickname, String imageUrl, int age, String city, Role role, SocialType socialType, String socialId, Member member) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.imageUrl = imageUrl;
        this.age = age;
        this.city = city;
        this.role = role;
        this.socialType = socialType;
        this.socialId = socialId;
        this.member = member;
    }
    public void joinUpdate(Member member){
        this.member = member;
    }
}
