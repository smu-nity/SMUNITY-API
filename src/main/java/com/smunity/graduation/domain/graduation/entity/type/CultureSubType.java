package com.smunity.graduation.domain.graduation.entity.type;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CultureSubType {

    // --------- 기초 ---------
    THINKING_AND_EXPRESSION("사고와표현"),
    ENGLISH("EnglishFoundations"),
    BASIC_MATH("기초수학"),
    COMPUTING("컴퓨팅사고와데이터의이해"),
    CULTURE("교양과인성"),
    // -----------------------

    // --------- 핵심 ---------
    DIVERSITY("다양성존중역량"),
    ETHICS("윤리실천역량"),
    CREATIVE("창의적문제해결역량"),
    EXPERTISE("전문지식탐구역량"),
    CONVERGENCE("융복합역량"),
    // -----------------------

    // --------- 균형 ---------
    HUMANITY("인문"),
    SOCIETY("사회"),
    NATURE("자연"),
    ENGINEERING("공학"),
    ART("예술"),
    BRIDGE("브리지"),
    // -----------------------

    // --------- 일반 ---------
    HISTORY("역사와철학"),
    LANGUAGE("언어와문화"),
    GLOBAL_LANGUAGE("글로벌과언어문화"),
    HUMAN("인간과사회"),
    LAW("법과정치"),
    ECONOMY("경제와경영"),
    SCIENCE("과학과환경"),
    LIFE("생활과건강"),
    INFORMATION("정보기술과산업"),
    ART_AND_DESIGN("예술과디자인"),
    ATHLETIC("체육과무용"),
    CHARACTER("인성과리더십"),
    CAREER("커리어디자인")

    // -----------------------
    ;

    private final String type;

    public static CultureSubType findBy(String arg) {
        for (CultureSubType cultureSubType : values()) {
            if (cultureSubType.getType().equals(arg)) {
                return cultureSubType;
            }
        }
        return null;
    }

    @JsonValue
    public String getType() {
        return this.type;
    }
}
