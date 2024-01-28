package com.smunity.graduation.domain.qna.dto;

import com.smunity.graduation.domain.qna.entity.Question;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Builder
public record QuestionListResponseDto(
        Long id,
        String type,
        String title,
        String author,
        LocalDateTime timestamp
) {

    private static String determineQuestionType(Question question) {
        return question.getAnswer() != null ? "답변완료" : "접수";
    }

    private static String determineAuthor(Question question) {
        return question.isAnonymous() ? "익명" : question.getAuthor().getUserName(); // 수정 필요
    }

    public static QuestionListResponseDto from(Question question) {
        return QuestionListResponseDto.builder()
                .id(question.getId())
                .type(determineQuestionType(question))
                .title(question.getTitle())
                .author(determineAuthor(question))
                .timestamp(question.getCreatedAt())
                .build();
    }

    public static List<QuestionListResponseDto> from(List<Question> questions) {
        return questions.stream()
                .map(QuestionListResponseDto::from)
                .collect(Collectors.toList());
    }
}
