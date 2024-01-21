package com.smunity.graduation.domain.courses.dto;

import jakarta.validation.constraints.NotBlank;

public record CourseRequestDto(
        @NotBlank(message = "[ERROR] 과목 pk 입력은 필수 입니다.")
        Long subjectId,
        @NotBlank(message = "[ERROR] 년도 입력은 필수 입니다.")
        String year,
        @NotBlank(message = "[ERROR] 학기 입력은 필수 입니다.")
        String semester,
        @NotBlank(message = "[ERROR] 이수구분 입력은 필수 입니다.")
        String type,
        @NotBlank(message = "[ERROR] 교양 영역명 입력은 필수 입니다.")
        String domain,
        @NotBlank(message = "[ERROR] 학점 입력은 필수 입니다.")
        int credit
) {
}
