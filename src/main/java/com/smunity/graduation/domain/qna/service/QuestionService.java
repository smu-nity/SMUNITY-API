package com.smunity.graduation.domain.qna.service;

import com.smunity.graduation.domain.accounts.entity.User;
import com.smunity.graduation.domain.qna.dto.QuestionRequestDto;
import com.smunity.graduation.domain.qna.dto.QuestionResponseDto;
import com.smunity.graduation.domain.qna.entity.Question;
import com.smunity.graduation.domain.qna.repository.QuestionJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class QuestionService {

    private final QuestionJpaRepository questionJpaRepository;
    private final QnAServiceUtils qnAServiceUtils;

    public QuestionResponseDto createQuestion(QuestionRequestDto requestDto) {
        User author = qnAServiceUtils.getCurrentUser();
        Question saveQuestion = questionJpaRepository.save(requestDto.toEntity(author));
        return QuestionResponseDto.from(saveQuestion);
    }

    public QuestionResponseDto updateQuestion(Long questionId, QuestionRequestDto requestDto) {
        User currentUser = qnAServiceUtils.getCurrentUser();
        Question existingQuestion  = qnAServiceUtils.getQuestionById(questionId);
        qnAServiceUtils.validateAuthorAccess(currentUser, existingQuestion.getAuthor().getUserName());
        existingQuestion.setTitle(requestDto.title());
        existingQuestion.setContent(requestDto.content());
        existingQuestion.setAnonymous(requestDto.anonymous());
        Question updateQuestion = questionJpaRepository.save(existingQuestion);
        return QuestionResponseDto.from(updateQuestion);
    }

    public void deleteQuestion(Long questionId) {
        User currentUser = qnAServiceUtils.getCurrentUser();
        Question existingQuestion = qnAServiceUtils.getQuestionById(questionId);
        qnAServiceUtils.validateAuthorAccess(currentUser, existingQuestion.getAuthor().getUserName());
        questionJpaRepository.delete(existingQuestion);
    }

}
