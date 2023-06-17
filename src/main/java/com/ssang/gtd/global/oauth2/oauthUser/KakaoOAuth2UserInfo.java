package com.ssang.gtd.global.oauth2.oauthUser;

import java.util.Map;
import java.util.Optional;

public class KakaoOAuth2UserInfo extends OAuth2UserInfo{
    public KakaoOAuth2UserInfo(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getId() {
        return String.valueOf(attributes.get("id"));
    }

    @Override
    public String getNickname() {
        return Optional.ofNullable((Map<String, Object>) attributes.get("kakao_account"))
                .map(account -> (Map<String, Object>) account.get("profile"))
                .map(profile -> (String) profile.get("nickname"))
                .orElse(null);
    }

    @Override
    public String getImageUrl() {
        return Optional.ofNullable((Map<String, Object>) attributes.get("kakao_account"))
                .map(account -> (Map<String, Object>) account.get("profile"))
                .map(profile -> (String) profile.get("thumbnail_image_url"))
                .orElse(null);
    }
}
