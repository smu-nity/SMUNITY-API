package com.smunity.graduation.domain.qna.service;

import com.smunity.graduation.domain.qna.dto.QuestionsResponseDto;
import com.smunity.graduation.domain.qna.entity.Question;
import com.smunity.graduation.domain.qna.repository.QuestionJpaRepository;
import com.smunity.graduation.global.common.ErrorCode;
import com.smunity.graduation.global.common.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class QuestionQueryService {

    private final QuestionJpaRepository questionJpaRepository;

    public Page<QuestionsResponseDto> getQuestionList(Pageable pageable) {
        Page<Question> questions = questionJpaRepository.findAll(pageable);
        return QuestionsResponseDto.from(questions);
    }

    public QuestionsResponseDto getQuestion(Long questionId) {
        Question question = questionJpaRepository.findById(questionId)
                .orElseThrow(() -> new CustomException(ErrorCode.QUESTION_NOT_FOUND));
        return QuestionsResponseDto.from(question);
    }
}
