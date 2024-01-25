package com.smunity.graduation.domain.qna.dto;

import com.smunity.graduation.domain.accounts.entity.User;
import com.smunity.graduation.domain.qna.entity.Answer;
import com.smunity.graduation.domain.qna.entity.Question;
import jakarta.validation.constraints.NotBlank;

public record AnswerRequestDto(
        Long questionId,
        @NotBlank(message = "[ERROR] 내용은 필수 입력 항목 입니다.")
        String content
) {
}
