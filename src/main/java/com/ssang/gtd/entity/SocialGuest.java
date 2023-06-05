package com.ssang.gtd.entity;

import com.ssang.gtd.oauth2.SocialType;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

@Embeddable
@Getter
@ToString
@NoArgsConstructor
public class SocialGuest {
    @Column(name = "nickname")
    private String nickname; // 닉네임
    @Column(name = "profileImg")
    private String profileImg; // 프로필 이미지
    @Column(name = "age")
    private int age;
    @Column(name = "city")
    private String city; // 사는 도시
    @Enumerated(EnumType.STRING)
    @Column(name = "social_type")
    private SocialType socialType; // KAKAO, NAVER, GOOGLE
    private String socialId; // 로그인한 소셜 타입의 식별자 값 (일반 로그인인 경우 null)

    @Builder
    public SocialGuest(String nickname, String profileImg, int age, String city, SocialType socialType, String socialId) {
        this.nickname = nickname;
        this.profileImg = profileImg;
        this.age = age;
        this.city = city;
        this.socialType = socialType;
        this.socialId = socialId;
    }


    // 유저 권한 설정 메소드
//    public void authorizeUser() {
//        this.role = Role.USER;
//    }
//
//    @Builder
//    public SocialMember(Long id, String email, String password, String nickname, String imageUrl, int age, String city, Role role, SocialType socialType, String socialId, Member member) {
//        this.id = id;
//        this.email = email;
//        this.password = password;
//        this.nickname = nickname;
//        this.imageUrl = imageUrl;
//        this.age = age;
//        this.city = city;
//        this.role = role;
//        this.socialType = socialType;
//        this.socialId = socialId;
//        this.member = member;
//    }
//    public void joinUpdate(Member member){
//        this.member = member;
//    }
}
