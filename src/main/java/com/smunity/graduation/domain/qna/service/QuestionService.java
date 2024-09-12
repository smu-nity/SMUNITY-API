package com.smunity.graduation.domain.qna.service;

import com.smunity.graduation.domain.accounts.entity.User;
import com.smunity.graduation.domain.qna.dto.QuestionRequestDto;
import com.smunity.graduation.domain.qna.dto.QuestionResponseDto;
import com.smunity.graduation.domain.qna.entity.Question;
import com.smunity.graduation.domain.qna.repository.QuestionJpaRepository;
import com.smunity.graduation.global.exception.code.ErrorCode;
import com.smunity.graduation.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class QuestionService {

    private final QuestionJpaRepository questionJpaRepository;

    public QuestionResponseDto createQuestion(QuestionRequestDto requestDto, User author) {
        Question question = requestDto.toEntity();
        question.setAuthor(author);
        Question saveQuestion = questionJpaRepository.save(question);
        return QuestionResponseDto.from(saveQuestion);
    }

    public QuestionResponseDto updateQuestion(Long questionId, QuestionRequestDto requestDto, User author) {
        Question existingQuestion = questionJpaRepository.findById(questionId)
                .orElseThrow(() -> new CustomException(ErrorCode.QUESTION_NOT_FOUND));
        validateAuthorAccess(author, existingQuestion.getAuthor().getUserName());
        existingQuestion.setTitle(requestDto.title());
        existingQuestion.setContent(requestDto.content());
        existingQuestion.setAnonymous(requestDto.anonymous());
        return QuestionResponseDto.from(existingQuestion);
    }

    public void deleteQuestion(Long questionId, User author) {
        Question existingQuestion = questionJpaRepository.findById(questionId)
                .orElseThrow(() -> new CustomException(ErrorCode.QUESTION_NOT_FOUND));
        validateAuthorAccess(author, existingQuestion.getAuthor().getUserName());
        questionJpaRepository.delete(existingQuestion);
    }

    // 작성자와 접근자가 같은 사람이거나 스태프 권한이 있는지 확인
    private void validateAuthorAccess(User currentUser, String authorUserName) {
        if (!currentUser.getUserName().equals(authorUserName) && !currentUser.isStaff()) {
            throw new CustomException(ErrorCode.AUTHOR_NOT_MATCHED);
        }
    }
}
