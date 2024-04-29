package com.smunity.graduation.domain.qna.service;

import com.smunity.graduation.domain.qna.dto.AnswerResponseDto;
import com.smunity.graduation.domain.qna.entity.Answer;
import com.smunity.graduation.domain.qna.repository.AnswerJpaRepository;
import com.smunity.graduation.global.common.ErrorCode;
import com.smunity.graduation.global.common.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class AnswerQueryService {

    private final AnswerJpaRepository answerJpaRepository;

    public AnswerResponseDto getAnswer(Long questionId) {
        Answer answer = answerJpaRepository.findByQuestionId(questionId)
                .orElseThrow(() -> new CustomException(ErrorCode.ANSWER_NOT_FOUND));
        return AnswerResponseDto.from(answer);
    }
}
