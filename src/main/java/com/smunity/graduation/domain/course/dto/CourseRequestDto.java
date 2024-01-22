package com.smunity.graduation.domain.course.dto;

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
        String domain,
        @NotBlank(message = "[ERROR] 학점 입력은 필수 입니다.")
        int credit
) {
        
}
