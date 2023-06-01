package com.ssang.gtd.test;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Gender implements EnumType {
    MALE("남자"),
    FEMALE("여자")
    ;

    private String description;

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public String getName() {
        return this.name();
    }
}