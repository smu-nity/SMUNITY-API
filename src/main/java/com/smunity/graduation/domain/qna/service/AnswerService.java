package com.smunity.graduation.domain.qna.service;

import com.smunity.graduation.domain.accounts.entity.User;
import com.smunity.graduation.domain.qna.dto.AnswerRequestDto;
import com.smunity.graduation.domain.qna.dto.AnswerResponseDto;
import com.smunity.graduation.domain.qna.entity.Answer;
import com.smunity.graduation.domain.qna.entity.Question;
import com.smunity.graduation.domain.qna.repository.AnswerJpaRepository;
import com.smunity.graduation.domain.qna.repository.QuestionJpaRepository;
import com.smunity.graduation.global.common.ErrorCode;
import com.smunity.graduation.global.common.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class AnswerService {

    private final QuestionJpaRepository questionJpaRepository;
    private final AnswerJpaRepository answerJpaRepository;

    public AnswerResponseDto createAnswer(Long questionId, AnswerRequestDto requestDto, User author) {
        validateStaffAccess(author);
        Question question = questionJpaRepository.findById(questionId)
                .orElseThrow(() -> new CustomException(ErrorCode.QUESTION_NOT_FOUND));
        Answer answer = requestDto.toEntity();
        answer.setAuthor(author);
        answer.setQuestion(question);
        Answer saveAnswer = answerJpaRepository.save(answer);
        return AnswerResponseDto.from(saveAnswer);
    }

    public AnswerResponseDto updateAnswer(Long questionId, AnswerRequestDto requestDto, User author) {
        validateStaffAccess(author);
        Answer existingAnswer = answerJpaRepository.findByQuestionId(questionId)
                .orElseThrow(() -> new CustomException(ErrorCode.ANSWER_NOT_FOUND));
        existingAnswer.setContent(requestDto.content());
        return AnswerResponseDto.from(existingAnswer);
    }

    public void deleteAnswer(Long questionId, User author) {
        validateStaffAccess(author);
        Answer existingAnswer = answerJpaRepository.findByQuestionId(questionId)
                .orElseThrow(() -> new CustomException(ErrorCode.ANSWER_NOT_FOUND));
        answerJpaRepository.delete(existingAnswer);
    }

    // 스태프 권한이 있는지 확인
    private void validateStaffAccess(User author) {
        if (!author.isStaff()) {
            throw new CustomException(ErrorCode.AUTHOR_IS_NOT_STAFF);
        }
    }
}
