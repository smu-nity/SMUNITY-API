package com.smunity.graduation.domain.qna.service;

import com.smunity.graduation.domain.qna.dto.QuestionResponseDto;
import com.smunity.graduation.domain.qna.entity.Question;
import com.smunity.graduation.domain.qna.repository.QuestionJpaRepository;
import com.smunity.graduation.global.exception.code.ErrorCode;
import com.smunity.graduation.global.exception.CustomException;
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

    public Page<QuestionResponseDto> getQuestionList(Pageable pageable) {
        Page<Question> questions = questionJpaRepository.findAll(pageable);
        return QuestionResponseDto.from(questions);
    }

    public QuestionResponseDto getQuestion(Long questionId) {
        Question question = questionJpaRepository.findById(questionId)
                .orElseThrow(() -> new CustomException(ErrorCode.QUESTION_NOT_FOUND));
        return QuestionResponseDto.from(question);
    }
}
