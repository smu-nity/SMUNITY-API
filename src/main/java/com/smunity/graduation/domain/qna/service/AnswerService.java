package com.smunity.graduation.domain.qna.service;

import com.smunity.graduation.domain.accounts.entity.User;
import com.smunity.graduation.domain.accounts.exception.AccountsExceptionHandler;
import com.smunity.graduation.domain.accounts.repository.user.UserRepository;
import com.smunity.graduation.domain.qna.dto.AnswerRequestDto;
import com.smunity.graduation.domain.qna.dto.AnswerResponseDto;
import com.smunity.graduation.domain.qna.entity.Answer;
import com.smunity.graduation.domain.qna.entity.Question;
import com.smunity.graduation.domain.qna.repository.AnswerJpaRepository;
import com.smunity.graduation.domain.qna.repository.QuestionJpaRepository;
import com.smunity.graduation.global.common.code.status.ErrorCode;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Transactional
@Service
public class AnswerService {

    private final AnswerJpaRepository answerJpaRepository;
    private final QuestionJpaRepository questionJpaRepository;
    private final UserRepository userRepository;

    public AnswerResponseDto createAnswer(Long questionId, AnswerRequestDto requestDto) {
        User author = userRepository.findByUserName("admin")
                .orElseThrow(() -> new AccountsExceptionHandler(ErrorCode.USER_NOT_FOUND));

        Question question = questionJpaRepository.findById(questionId)
                .orElseThrow(() -> new AccountsExceptionHandler(ErrorCode.QUESTION_NOT_FOUND));

        Answer saveAnswer = answerJpaRepository.save(requestDto.toEntity(author, question));
        return AnswerResponseDto.from(saveAnswer);
    }

    public AnswerResponseDto updateAnswer(Long answerId, AnswerRequestDto requestDto) {
        Answer existingAnswer = answerJpaRepository.findById(answerId)
                .orElseThrow(() -> new AccountsExceptionHandler(ErrorCode.ANSWER_NOT_FOUND));
        requestDto.updateFromDto(existingAnswer);
        Answer updateAnswer = answerJpaRepository.save(existingAnswer);
        return AnswerResponseDto.from(updateAnswer);
    }

    public void deleteAnswer(Long answerId) {
        Answer existingAnswer = answerJpaRepository.findById(answerId)
                .orElseThrow(() -> new AccountsExceptionHandler(ErrorCode.ANSWER_NOT_FOUND));
        answerJpaRepository.delete(existingAnswer);
    }
}
