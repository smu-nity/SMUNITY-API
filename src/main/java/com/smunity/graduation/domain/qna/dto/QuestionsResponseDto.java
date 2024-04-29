package com.smunity.graduation.domain.qna.dto;

import com.smunity.graduation.domain.qna.entity.Question;
import com.smunity.graduation.domain.qna.entity.QuestionStatus;
import lombok.Builder;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record QuestionsResponseDto(
        Long id,
        QuestionStatus type,
        String title,
        String name,
        LocalDateTime timestamp
) {

    public static QuestionsResponseDto from(Question question) {
        String authorName = question.isAnonymous() ? "익명" : question.getAuthor().getName();
        return QuestionsResponseDto.builder()
                .id(question.getId())
                .type(QuestionStatus.from(question))
                .title(question.getTitle())
                .name(authorName)
                .timestamp(question.getCreatedAt())
                .build();
    }

    public static List<QuestionsResponseDto> from(List<Question> questions) {
        return questions.stream()
                .map(QuestionsResponseDto::from)
                .toList();
    }

    public static Page<QuestionsResponseDto> from(Page<Question> questions) {
        return questions.map(QuestionsResponseDto::from);
    }
}
