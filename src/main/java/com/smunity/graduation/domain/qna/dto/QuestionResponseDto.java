package com.smunity.graduation.domain.qna.dto;

import com.smunity.graduation.domain.qna.entity.Question;
import lombok.Builder;

import java.time.LocalDateTime;
@Builder
public record QuestionResponseDto(
        Long id,
        String title,
        String content,
        String name,
        boolean anonymous,
        LocalDateTime timestamp
) {
    public static QuestionResponseDto from(Question question) {
        return QuestionResponseDto.builder()
                .id(question.getId())
                .title(question.getTitle())
                .content(question.getContent())
                .name(question.getAuthor().getName())
                .anonymous(question.isAnonymous())
                .timestamp(question.getCreatedAt())
                .build();
    }
}
