package com.ssang.gtd.oauth2.oauthUser;

import java.util.Map;
import java.util.Optional;

public class NaverOAuth2UserInfo extends OAuth2UserInfo{
    public NaverOAuth2UserInfo(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getId() {
        return Optional.ofNullable((Map<String, Object>) attributes.get("response"))
                .map(response -> (String) response.get("id")).orElse(null);
    }

    @Override
    public String getNickname() {
        return Optional.ofNullable((Map<String, Object>) attributes.get("response"))
                .map(response -> (String) response.get("nickname")).orElse(null);
    }

    @Override
    public String getImageUrl() {
        return Optional.ofNullable((Map<String, Object>) attributes.get("response"))
                .map(response -> (String) response.get("profile_image")).orElse(null);
    }
}
