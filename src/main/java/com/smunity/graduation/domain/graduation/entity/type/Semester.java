package com.smunity.graduation.domain.graduation.entity.type;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Semester {

    ALL("전체학기"),
    FIRST("1학기"),
    SECOND("2학기"),
    SUMMER("하계"),
    WINTER("동계");


    private final String semester;

    public static Semester findBy(String arg) {
        for (Semester semester : values()) {
            if (semester.getSemester().equals(arg)) {
                return semester;
            }
        }
        return null;
    }

    @JsonValue
    public String getSemester() {
        return this.semester;
    }

}
