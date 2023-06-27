package com.ssang.gtd.domain.user.domain;

import com.ssang.gtd.global.enums.Role;
import com.ssang.gtd.global.oauth2.SocialType;
import com.ssang.gtd.global.enums.Gender;
import com.ssang.gtd.global.enums.MemberStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name="MemberSocialType")
@DiscriminatorValue("MemberSocialType")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberSocial extends MemberDetail {
    @Column(name = "nickname")
    private String nickname;
    @Column(name = "profileImg")
    private String profileImg;
    @Column(name = "age")
    private int age;
    @Column(name = "city")
    private String city;
    @Enumerated(EnumType.STRING)
    @Column(name = "social_type")
    private SocialType socialType;
    private String socialId; // 로그인한 소셜 타입의 식별자 값 (일반 로그인인 경우 null)

    public MemberSocial(String email, Role role, MemberStatus status, String nickname, String profileImg, SocialType socialType, String socialId) {
        super(email, role, status);
        this.nickname = nickname;
        this.profileImg = profileImg;
        this.socialType = socialType;
        this.socialId = socialId;
    }

    public MemberSocial(Long id) {
        super(id);
    }

    public MemberSocial(String email, String password, String userName, String name, Gender gender, Role role, MemberStatus status) {
        super(email, password, userName, name, gender, role, status);
    }
}
