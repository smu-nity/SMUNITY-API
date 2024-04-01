package com.smunity.graduation.global.common.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Category {
    MAJOR_ADVANCED("1전심"),
    MAJOR_OPTIONAL("1전선"),
    CULTURE_REQUIRED("교필"),
    CULTURE_OPTIONAL("교선"),
    ETC("기타");

    private final String name;
}
