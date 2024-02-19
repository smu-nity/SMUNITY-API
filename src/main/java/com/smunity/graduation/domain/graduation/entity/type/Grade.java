package com.smunity.graduation.domain.graduation.entity.type;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Grade {

    ALL(0, "전체학년"),
    FRESHMAN(1, "1학년"),
    SOPHOMORE(2, "2학년"),
    JUNIOR(3, "3학년"),
    SENIOR(4, "4학년");

    private final Integer value;
    private final String grade;

    public static Grade findBy(String arg) {
        for (Grade grade : values()) {
            if (grade.getGrade().equals(arg)) {
                return grade;
            }
        }
        return null;
    }

    @JsonValue
    public String getGrade() {
        return this.grade;
    }

}
