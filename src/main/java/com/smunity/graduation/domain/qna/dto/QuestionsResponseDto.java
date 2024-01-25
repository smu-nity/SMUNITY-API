package com.smunity.graduation.domain.qna.dto;

import com.smunity.graduation.domain.qna.entity.Question;
import com.smunity.graduation.domain.qna.entity.QuestionStatus;
import com.smunity.graduation.domain.qna.service.QuestionQueryService;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Builder
public record QuestionsResponseDto(
        Long id,
        QuestionStatus type,
        String title,
        String author,
        LocalDateTime timestamp
) {

    public static QuestionsResponseDto from(Question question) {
        return QuestionsResponseDto.builder()
                .id(question.getId())
                .type(QuestionStatus.from(question))
                .title(question.getTitle())
//                .author(question.getAuthor())   //ToDo: User 수정 후 username을 가져오기
                .timestamp(question.getCreatedAt())
                .build();
    }

    public static List<QuestionsResponseDto> from(List<Question> questions) {
        return questions.stream()
                .map(QuestionsResponseDto::from)
                .collect(Collectors.toList());
    }
}
