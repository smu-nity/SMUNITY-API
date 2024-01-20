package com.smunity.graduation.domain.smu.dto;

public record CourseResponseDto(
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
