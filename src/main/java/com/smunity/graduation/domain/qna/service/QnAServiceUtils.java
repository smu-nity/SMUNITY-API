package com.smunity.graduation.domain.qna.service;

import com.smunity.graduation.domain.accounts.exception.AccountsExceptionHandler;
import com.smunity.graduation.domain.qna.entity.Answer;
import com.smunity.graduation.domain.qna.entity.Question;
import com.smunity.graduation.domain.qna.repository.AnswerJpaRepository;
import com.smunity.graduation.domain.qna.repository.QuestionJpaRepository;
import com.smunity.graduation.global.common.code.status.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class QnAServiceUtils {

    private final AnswerJpaRepository answerJpaRepository;
    private final QuestionJpaRepository questionJpaRepository;

    public Answer getAnswerById(Long answerId) {
        return answerJpaRepository.findById(answerId)
                .orElseThrow(() -> new AccountsExceptionHandler(ErrorCode.ANSWER_NOT_FOUND));
    }

    public Question getQuestionById(Long questionId) {
        return questionJpaRepository.findById(questionId)
                .orElseThrow(() -> new AccountsExceptionHandler(ErrorCode.QUESTION_NOT_FOUND));
    }
}
