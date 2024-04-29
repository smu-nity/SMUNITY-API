package com.smunity.graduation.domain.qna.dto;

import com.smunity.graduation.domain.qna.entity.Answer;
import jakarta.validation.constraints.NotBlank;

public record AnswerRequestDto(
        @NotBlank(message = "[ERROR] 내용은 필수 입력 항목 입니다.")
        String content
) {

    public Answer toEntity() {
        return Answer.builder()
                .content(content)
                .build();
    }
}
