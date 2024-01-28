package com.smunity.graduation.domain.qna.dto;

import com.smunity.graduation.domain.qna.entity.Answer;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record AnswerResponseDto(
        Long id,
        Long questionId,
        String content,
        LocalDateTime timestamp

) {
    public static AnswerResponseDto from(Answer answer) {
        return AnswerResponseDto.builder()
                .id(answer.getId())
                .questionId(answer.getQuestion().getId())
                .content(answer.getContent())
                .build();
    }
}
