package com.smunity.graduation.domain.graduation.entity.type;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SubjectType {

    ORDINARY_SELECT("일선"),
    CULTURE_SELECT("교선"),
    CULTURE_ESSENTIAL("교필"),
    MAJOR_SELECT("1전선"),
    MAJOR_ADVANCED("1전심"),
    MAJOR_REQUIRED("1전필"),
    MAJOR_TEACHING("1교직");

    private final String type;

    public static SubjectType findBy(String arg) {
        for (SubjectType subjectType : values()) {
            if (subjectType.getType().equals(arg)) {
                return subjectType;
            }
        }
        return null;
    }

    @JsonValue
    public String getType() {
        return this.type;
    }
}
