package com.smunity.graduation.domain.course.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CourseRequestDto(
        @NotBlank(message = "[ERROR] 과목명 입력은 필수 입니다.")
        String name,
        @NotBlank(message = "[ERROR] 학수번호 입력은 필수 입니다.")
        @Size(min = 8, max = 8, message = "[ERROR] 학수번호는 8자리여야 합니다.")
        String number,
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
