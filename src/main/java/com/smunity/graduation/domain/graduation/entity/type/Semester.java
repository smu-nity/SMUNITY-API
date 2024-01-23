package com.smunity.graduation.domain.graduation.entity.type;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Semester {

    FIRST("1학기"),
    SECOND("2학기"),
    SUMMER("하계"),
    WINTER("동계");


    private final String semester;

    @JsonValue
    public String getSemester() {
        return this.semester;
    }

}
