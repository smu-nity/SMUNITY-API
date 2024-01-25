package com.smunity.graduation.domain.qna.service;

import com.smunity.graduation.domain.accounts.entity.User;
import com.smunity.graduation.domain.accounts.exception.AccountsExceptionHandler;
import com.smunity.graduation.domain.accounts.repository.user.UserRepository;
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
    private final UserRepository userRepository;
    private final QnAServiceUtils qnAServiceUtils;

    //ToDo: 인증된 사용자 가져 오기
    public QuestionResponseDto createQuestion(QuestionRequestDto requestDto) {
        User author = userRepository.findByUserName("admin")
                .orElseThrow(() -> new AccountsExceptionHandler(ErrorCode.USER_NOT_FOUND));
        Question saveQuestion = questionJpaRepository.save(requestDto.toEntity(author));
        return QuestionResponseDto.from(saveQuestion);
    }

    //ToDo: 유저가 작성한 글인지 확인
    public QuestionResponseDto updateQuestion(Long questionId, QuestionRequestDto requestDto) {
        Question existingQuestion  = qnAServiceUtils.getQuestionById(questionId);
        existingQuestion.setTitle(requestDto.title());
        existingQuestion.setContent(requestDto.content());
        existingQuestion.setAnonymous(requestDto.anonymous());
        Question updateQuestion = questionJpaRepository.save(existingQuestion);
        return QuestionResponseDto.from(updateQuestion);
    }

    //ToDo: 유저가 작성한 글인지 확인
    public void deleteQuestion(Long questionId) {
        Question existingQuestion = qnAServiceUtils.getQuestionById(questionId);
        questionJpaRepository.delete(existingQuestion);
    }

}
