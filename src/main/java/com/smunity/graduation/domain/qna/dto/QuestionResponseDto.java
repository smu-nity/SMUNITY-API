package com.smunity.graduation.domain.qna.dto;

import com.smunity.graduation.domain.qna.entity.Question;
import lombok.Builder;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record QuestionResponseDto(
        Long id,
        String author,
        String title,
        String content,
        boolean answered,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {

    public static QuestionResponseDto from(Question question) {
        return QuestionResponseDto.builder()
                .id(question.getId())
                .author(question.isAnonymous() ? "익명" : question.getAuthor().getName())
                .title(question.getTitle())
                .content(question.getContent())
                .answered(question.getAnswer() != null)
                .createdAt(question.getCreatedAt())
                .updatedAt(question.getUpdatedAt())
                .build();
    }

    public static List<QuestionResponseDto> from(List<Question> questions) {
        return questions.stream()
                .map(QuestionResponseDto::from)
                .toList();
    }

    public static Page<QuestionResponseDto> from(Page<Question> questions) {
        return questions.map(QuestionResponseDto::from);
    }
}
