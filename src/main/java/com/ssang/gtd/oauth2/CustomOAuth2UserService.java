package com.ssang.gtd.oauth2;

import com.ssang.gtd.entity.SocialMember;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final SocialMemberRepository socialmemberRepository;
    private static final String NAVER = "naver";
    private static final String KAKAO = "kakao";
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        // === 소셜 로그인 API의 사용자 정보 제공 URI로 요청을 보내서 사용자 정보를 얻은 후 OAuth 서비스에서 가져온 유저 정보를 반환 === //
        OAuth2User oAuth2User = new DefaultOAuth2UserService().loadUser(userRequest);

        // === userRequest에서 registrationId 추출 === //
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        // === SocialType 저장 === //
        SocialType socialType = getSocialType(registrationId);

        // === userNameAttributeName은 이후에 nameAttributeKey로 설정된다. === //
        String userNameAttributeName = userRequest.getClientRegistration()
                .getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName(); // OAuth2 로그인 시 키(PK)가 되는 값
        Map<String, Object> attributes = oAuth2User.getAttributes(); // 소셜 로그인에서 API가 제공하는 userInfo의 Json 값(유저 정보들)

        // === socialType에 따라 유저 정보를 통해 OAuthAttributes 객체 생성 === //
        OAuthAttributes extractAttributes = OAuthAttributes.of(socialType, userNameAttributeName, attributes);

        // === getUser() 메소드로 User 객체 생성 후 반환 === //
        SocialMember createdUser = getUser(extractAttributes, socialType);

        // === DefaultOAuth2User를 구현한 CustomOAuth2User 객체를 생성해서 반환 === //
        return new CustomOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(createdUser.getRole().getKey())),
                attributes,
                extractAttributes.getNameAttributeKey(),
                createdUser.getEmail(),
                createdUser.getRole()
        );
    }

    private SocialType getSocialType(String registrationId) {
        if(NAVER.equals(registrationId)) {
            return SocialType.NAVER;
        }
        if(KAKAO.equals(registrationId)) {
            return SocialType.KAKAO;
        }
        return SocialType.GOOGLE;
    }

    /**
     * SocialType과 attributes에 들어있는 소셜 로그인의 식별값 id를 통해 회원을 찾아 반환하는 메소드
     * 만약 찾은 회원이 있다면, 그대로 반환하고 없다면 saveUser()를 호출하여 회원을 저장한다.
     */
    private SocialMember getUser(OAuthAttributes attributes, SocialType socialType) {
        SocialMember findUser = socialmemberRepository.findBySocialTypeAndSocialId(socialType,
                attributes.getOauth2UserInfo().getId()).orElse(null);

        if(findUser == null) {
            return saveUser(attributes, socialType);
        }
        return findUser;
    }

    /**
     * OAuthAttributes의 toEntity() 메소드를 통해 빌더로 Member 객체 생성 후 반환
     * 생성된 Member 객체를 DB에 저장 : socialType, socialId, email, role 값만 있는 상태
     */
    private SocialMember saveUser(OAuthAttributes attributes, SocialType socialType) {
        SocialMember createdUser = attributes.toEntity(socialType, attributes.getOauth2UserInfo());
        return socialmemberRepository.save(createdUser);
    }
}
