package com.smunity.graduation.domain.qna.dto;

import com.smunity.graduation.domain.accounts.entity.User;
import com.smunity.graduation.domain.qna.entity.Question;
import lombok.Builder;

import java.time.LocalDateTime;
@Builder
public record QuestionResponseDto(
        Long id,
        String title,
        String content,
        User author,
        boolean anonymous,
        LocalDateTime timestamp
) {
    public static QuestionResponseDto from(Question question) {
        return QuestionResponseDto.builder()
                .id(question.getId())
                .title(question.getTitle())
                .content(question.getContent())
                .author(question.getAuthor())
                .anonymous(question.isAnonymous())
                .timestamp(question.getCreatedAt())
                .build();
    }
}
