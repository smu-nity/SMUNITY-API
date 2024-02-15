package com.smunity.graduation.domain.qna.service;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smunity.graduation.domain.accounts.entity.User;
import com.smunity.graduation.domain.accounts.exception.AccountsExceptionHandler;
import com.smunity.graduation.domain.accounts.repository.user.UserJpaRepository;
import com.smunity.graduation.domain.qna.entity.Answer;
import com.smunity.graduation.domain.qna.entity.Question;
import com.smunity.graduation.domain.qna.repository.AnswerJpaRepository;
import com.smunity.graduation.domain.qna.repository.QuestionJpaRepository;
import com.smunity.graduation.global.common.ErrorCode;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class QnAServiceUtils {

	private final AnswerJpaRepository answerJpaRepository;
	private final QuestionJpaRepository questionJpaRepository;
	private final UserJpaRepository userJpaRepository;

	public Answer getAnswerById(Long answerId) {
		return answerJpaRepository.findById(answerId)
			.orElseThrow(() -> new AccountsExceptionHandler(ErrorCode.ANSWER_NOT_FOUND));
	}

	public Question getQuestionById(Long questionId) {
		return questionJpaRepository.findById(questionId)
			.orElseThrow(() -> new AccountsExceptionHandler(ErrorCode.QUESTION_NOT_FOUND));
	}

	// 작성자와 접근자가 같은 사람인지 확인
	public void validateAuthorAccess(User currentUser, String authorUserName) {
		if (!currentUser.getUserName().equals(authorUserName)) {
			throw new AccountsExceptionHandler(ErrorCode.AUTHOR_NOT_MATCHED);
		}
	}

	// 스태프 권한이 있는지 확인
	public void validateStaffAccess(User Author) {
		if (!Author.getIsStaff()) {
			throw new AccountsExceptionHandler(ErrorCode.AUTHOR_IS_NOT_STAFF);
		}
	}
}
