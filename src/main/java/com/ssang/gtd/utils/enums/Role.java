package com.ssang.gtd.utils.enums;

import lombok.Getter;

@Getter
public enum Role implements EnumType {
    GUEST("ROLE_GUEST")
    ,USER("ROLE_USER")
    ,ADMIN("ROLE_ADMIN")
    ;

    Role(String key) {
        this.key = key;
    }

    private final String key;
    @Override
    public String getDescription() {
        return this.key;
    }

    @Override
    public String getName() {
        return this.name();
    }
}
