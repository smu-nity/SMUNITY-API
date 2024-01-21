package com.smunity.graduation.domain.smu.dto;

public record CourseResponseDto(
        Long id,
        Long subjectId,
        Long userId,
        String year,
        String semester,
        String type,
        String domain,
        int credit,
        boolean custom
) {
}
