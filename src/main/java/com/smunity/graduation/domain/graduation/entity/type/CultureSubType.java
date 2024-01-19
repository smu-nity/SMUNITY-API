package com.smunity.graduation.domain.graduation.entity.type;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CultureSubType {

    EXPERTISE("전문지식탐구역량"),
    CREATIVE("창의적문제해결역량"),
    CONVERGENCE("융복합역량"),
    DIVERSITY("다양성존중역량"),
    ETHICS("윤리실천역량"),
    HUMANITIES("인문"),
    SOCIETY("사회"),
    NATURE("자연"),
    ENGINEERING("공학"),
    ART("예술"),
    ;

    private final String type;

    @JsonValue
    public String getType() {
        return this.type;
    }

}
