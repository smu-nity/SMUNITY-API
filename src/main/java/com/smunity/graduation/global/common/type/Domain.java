package com.smunity.graduation.global.common.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Domain {

    BASIC("기초"),
    CORE("핵심"),
    BALANCE("균형");

    private final String name;
}
