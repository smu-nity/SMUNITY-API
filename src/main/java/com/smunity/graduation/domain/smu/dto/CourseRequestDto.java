package com.smunity.graduation.domain.smu.dto;

public record CourseRequestDto(
        String number,
        String name,
        String type,
        String grade,
        String year,
        String semester,
        String domain,
        int credit
) {
}
