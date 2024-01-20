package com.smunity.graduation.domain.qna.service;

import com.smunity.graduation.domain.accounts.exception.AccountsExceptionHandler;
import com.smunity.graduation.domain.qna.dto.QuestionRequestDto;
import com.smunity.graduation.domain.qna.dto.QuestionResponseDto;
import com.smunity.graduation.domain.qna.entity.Question;
import com.smunity.graduation.domain.qna.repository.QuestionJpaRepository;
import com.smunity.graduation.global.common.code.status.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class QuestionService {
    private final QuestionJpaRepository questionJpaRepository;

//    public QuestionResponseDto createQuestion(QuestionRequestDto requestDto) {
//
////        Question saveQuestion = questionJpaRepository.save(requestDto.toEntity());
////        return QuestionResponseDto.from(saveQuestion);
//    }

    public QuestionResponseDto updateQuestion(Long questionId, QuestionRequestDto requestDto) {
        Question existingQuestion  = questionJpaRepository.findById(questionId)
                .orElseThrow(() -> new AccountsExceptionHandler(ErrorCode.QUESTION_NOT_FOUND));
        requestDto.updateFromDto(existingQuestion);
        Question updateQuestion = questionJpaRepository.save(existingQuestion);
        return QuestionResponseDto.from(updateQuestion);
    }

    public void deleteQuestion(Long questionId) {
        Question existingQuestion = questionJpaRepository.findById(questionId)
                .orElseThrow(() -> new AccountsExceptionHandler(ErrorCode.QUESTION_NOT_FOUND));
        questionJpaRepository.delete(existingQuestion);
    }
}
