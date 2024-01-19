package com.smunity.graduation.domain.graduation.entity.type;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Semester {

    FIRST(1, "1학기"),
    SECOND(2, "2학기"),
    SUMMER(3, "하계"),
    WINTER(4, "동계");

    private final Integer value;
    private final String semester;

    @JsonValue
    public String getSemester() {
        return this.semester;
    }

}
