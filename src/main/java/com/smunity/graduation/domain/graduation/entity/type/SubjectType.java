package com.smunity.graduation.domain.graduation.entity.type;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SubjectType {

    EDUCATION("1교직"),
    MAJOR_S("1전선"),
    MAJOR_I("1전심"),
    CULTURE_E("교필"),
    CULTURE_S("교선"),
    ;

    private final String type;

    @JsonValue
    public String getType() {
        return this.type;
    }

}
