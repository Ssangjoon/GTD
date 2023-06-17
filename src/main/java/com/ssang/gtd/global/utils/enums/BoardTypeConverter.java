package com.ssang.gtd.global.utils.enums;

import jakarta.persistence.AttributeConverter;

import java.util.EnumSet;
import java.util.NoSuchElementException;

public class BoardTypeConverter implements AttributeConverter<BoardType, String> {
    @Override
    public String convertToDatabaseColumn(BoardType attribute) {
        return attribute.getType();
    }

    @Override
    public BoardType convertToEntityAttribute(String dbData) {
        return EnumSet.allOf(BoardType.class).stream()
                .filter(e->e.getType().equals(dbData))
                .findAny()
                .orElseThrow(()-> new NoSuchElementException());
    }
}
