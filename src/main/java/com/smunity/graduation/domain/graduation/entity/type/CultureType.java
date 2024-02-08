package com.smunity.graduation.domain.graduation.entity.type;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CultureType {

    CULTURE("교양"),
    CULTURE_B("기초"),
    CULTURE_E("핵심"),
    CULTURE_S("균형"),
    ;

    private final String type;

    @JsonValue
    public String getType() {
        return this.type;
    }

}
