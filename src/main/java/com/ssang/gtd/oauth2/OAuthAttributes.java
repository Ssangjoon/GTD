package com.ssang.gtd.oauth2;

import com.ssang.gtd.entity.MemberSocial;
import com.ssang.gtd.oauth2.oauthUser.GoogleOAuth2UserInfo;
import com.ssang.gtd.oauth2.oauthUser.KakaoOAuth2UserInfo;
import com.ssang.gtd.oauth2.oauthUser.NaverOAuth2UserInfo;
import com.ssang.gtd.oauth2.oauthUser.OAuth2UserInfo;
import com.ssang.gtd.utils.enums.MemberStatus;
import com.ssang.gtd.utils.enums.Role;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;
import java.util.UUID;

/**
 * 각 소셜에서 받아오는 데이터가 다르므로
 * 소셜별로 데이터를 받는 데이터를 분기 처리하는 DTO 클래스
 */
@Getter
public class OAuthAttributes {

    private String nameAttributeKey; // OAuth2 로그인 진행 시 키가 되는 필드 값, PK와 같은 의미
    private OAuth2UserInfo oauth2UserInfo; // 소셜 타입별 로그인 유저 정보(닉네임, 이메일, 프로필 사진 등등)

    @Builder
    public OAuthAttributes(String nameAttributeKey, OAuth2UserInfo oauth2UserInfo) {
        this.nameAttributeKey = nameAttributeKey;
        this.oauth2UserInfo = oauth2UserInfo;
    }

    /**
     * SocialType에 맞는 메소드 호출하여 OAuthAttributes 객체를 반환한다.
     * 각 메서드는 소셜 타입별로 나눠서 빌더로 OAuthAttribute 빌드 시 유저 정보 추상 클래스인
     *  OAuth2UserInfo 필드에 각 소셜 타입의 OAuth2UserInfo를 생성하여 빌드
     */
    public static OAuthAttributes of(SocialType socialType,
                                     String userNameAttributeName, Map<String, Object> attributes) {

        if (socialType == SocialType.NAVER) {
            return ofNaver(userNameAttributeName, attributes);
        }
        if (socialType == SocialType.KAKAO) {
            return ofKakao(userNameAttributeName, attributes);
        }
        return ofGoogle(userNameAttributeName, attributes);
    }

    private static OAuthAttributes ofKakao(String userNameAttributeName, Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .nameAttributeKey(userNameAttributeName)
                .oauth2UserInfo(new KakaoOAuth2UserInfo(attributes))
                .build();
    }

    public static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .nameAttributeKey(userNameAttributeName)
                .oauth2UserInfo(new GoogleOAuth2UserInfo(attributes))
                .build();
    }

    public static OAuthAttributes ofNaver(String userNameAttributeName, Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .nameAttributeKey(userNameAttributeName)
                .oauth2UserInfo(new NaverOAuth2UserInfo(attributes))
                .build();
    }

    /**
     * OAuthAttibutes 정보로 내 서비스 User를 생성하는 toEntity메서드
     *
     * of메소드로 OAuthAttributes 객체가 생성되어, 유저 정보들이 담긴 OAuth2UserInfo가 소셜 타입별로 주입된 상태
     * OAuth2UserInfo에서 socialId(식별값), nickname, imageUrl을 가져와서 build
     * email에는 UUID로 중복 없는 랜덤 값 생성 role은 GUEST로 설정
     * (email은 JWT Token을 발급하기 위한 용도뿐이므로 UUID를 사용하여 임의로 설정.)
     */

    public MemberSocial toEntity(SocialType socialType, OAuth2UserInfo oauth2UserInfo) {
        MemberSocial socialMember = new MemberSocial(
                UUID.randomUUID() + "@socialUser.com",
                Role.GUEST,
                MemberStatus.NORMAL,
                oauth2UserInfo.getNickname(),
                oauth2UserInfo.getImageUrl(),
                socialType,
                oauth2UserInfo.getId()
                );
        return socialMember;
    }
}