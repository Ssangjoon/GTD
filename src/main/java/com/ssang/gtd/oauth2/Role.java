package com.ssang.gtd.oauth2;

import lombok.Getter;

@Getter
public enum Role {
    GUEST("ROLE_GUEST")
    ,USER("ROLE_USER")
    ,ADMIN("ROLE_ADMIN")
    ;

    Role(String key) {
        this.key = key;
    }

    private final String key;
}
