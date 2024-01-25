package com.smunity.graduation.domain.qna.dto;

import com.smunity.graduation.domain.accounts.entity.User;
import com.smunity.graduation.domain.qna.entity.Question;
import jakarta.validation.constraints.NotBlank;

public record QuestionRequestDto(
        @NotBlank(message = "[ERROR] 제목 입력은 필수 입니다.")
        String title,
        @NotBlank(message = "[ERROR] 내용은 필수 입력 항목 입니다.")
        String content,
        boolean anonymous

) {
        public Question toEntity(User author) {
                return Question.builder()
                        .title(title)
                        .content(content)
                        .anonymous(anonymous)
                        .author(author)
                        .build();
        }

}