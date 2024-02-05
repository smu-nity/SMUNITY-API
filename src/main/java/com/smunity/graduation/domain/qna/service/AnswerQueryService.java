package com.smunity.graduation.domain.qna.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smunity.graduation.domain.accounts.jwt.exception.AccountsExceptionHandler;
import com.smunity.graduation.domain.qna.dto.AnswerResponseDto;
import com.smunity.graduation.domain.qna.entity.Answer;
import com.smunity.graduation.domain.qna.repository.AnswerJpaRepository;
import com.smunity.graduation.global.common.ErrorCode;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class AnswerQueryService {

	private final AnswerJpaRepository answerJpaRepository;

	public AnswerResponseDto getAnswer(Long questionId) {
		Answer answer = answerJpaRepository.findByQuestionId(questionId)
			.orElseThrow(() -> new AccountsExceptionHandler(ErrorCode.QUESTION_NOT_FOUND));
		return AnswerResponseDto.from(answer);
	}
}
