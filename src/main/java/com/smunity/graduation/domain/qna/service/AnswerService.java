package com.smunity.graduation.domain.qna.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smunity.graduation.domain.accounts.entity.User;
import com.smunity.graduation.domain.qna.dto.AnswerRequestDto;
import com.smunity.graduation.domain.qna.dto.AnswerResponseDto;
import com.smunity.graduation.domain.qna.entity.Answer;
import com.smunity.graduation.domain.qna.entity.Question;
import com.smunity.graduation.domain.qna.repository.AnswerJpaRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class AnswerService {

	private final AnswerJpaRepository answerJpaRepository;
	private final QnAServiceUtils qnAServiceUtils;

	public AnswerResponseDto createAnswer(Long questionId, AnswerRequestDto requestDto, User author) {
		Question question = qnAServiceUtils.getQuestionById(questionId);
		qnAServiceUtils.validateStaffAccess(author);
		Answer answer = requestDto.toEntity(author, question);
		Answer saveAnswer = answerJpaRepository.save(answer);
		return AnswerResponseDto.from(saveAnswer);
	}

	public AnswerResponseDto updateAnswer(Long answerId, AnswerRequestDto requestDto, User author) {
		Answer existingAnswer = qnAServiceUtils.getAnswerById(answerId);
		qnAServiceUtils.validateStaffAccess(author);
		existingAnswer.setContent(requestDto.content());
		// Answer updateAnswer = answerJpaRepository.save(existingAnswer);
		return AnswerResponseDto.from(existingAnswer);
	}

	public void deleteAnswer(Long answerId, User author) {
		Answer existingAnswer = qnAServiceUtils.getAnswerById(answerId);
		qnAServiceUtils.validateStaffAccess(author);
		answerJpaRepository.delete(existingAnswer);
	}
}
