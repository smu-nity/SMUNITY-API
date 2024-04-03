package com.smunity.graduation.global.common.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.stream.Stream;

@Getter
@RequiredArgsConstructor
public enum SubDomain {

    BASIC_ACCIDENT("사고와표현"),
    BASIC_ENG_MATH("기초영어/기초수학"),
    BASIC_COMPUTER_1("컴퓨팅사고와데이터의이해"),
    BASIC_COMPUTER_2("알고리즘과게임콘텐츠"),
    BASIC_COMPUTER("컴퓨팅사고와문제해결"),
    CORE_PROFESSIONAL("전문지식탐구역량"),
    CORE_CREATIVE("창의적문제해결역량"),
    CORE_CONVERGENCE("융복합역량"),
    CORE_DIVERSITY("다양성존중역량"),
    CORE_ETHICAL("윤리실천역량"),
    BALANCE_HUMANITIES("인문"),
    BALANCE_SOCIAL("사회"),
    BALANCE_NATURAL("자연"),
    BALANCE_ENGINEER("공학"),
    BALANCE_ART("예술"),
    BALANCE_BRIDGE("브리지"),
    BALANCE_NATURAL_ENGINEER("자연/공학");

    private final String name;

    public static SubDomain of(String name) {
        return hasEngMath(name) ? BASIC_ENG_MATH : findByName(name);
    }

    private static SubDomain findByName(String name) {
        return Arrays.stream(values())
                .filter(subDomain -> name.contains(subDomain.getName()))
                .findFirst()
                .orElse(null);
    }

    private static boolean hasEngMath(String name) {
        return Stream.of("English", "영어", "수학", "미적분학")
                .anyMatch(name::contains);
    }
}
