package com.smunity.graduation.domain.qna.service;

import com.smunity.graduation.domain.accounts.entity.User;
import com.smunity.graduation.domain.qna.dto.AnswerRequestDto;
import com.smunity.graduation.domain.qna.dto.AnswerResponseDto;
import com.smunity.graduation.domain.qna.entity.Answer;
import com.smunity.graduation.domain.qna.entity.Question;
import com.smunity.graduation.domain.qna.repository.AnswerJpaRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Transactional
@Service
public class AnswerService {

    private final AnswerJpaRepository answerJpaRepository;
    private final QnAServiceUtils qnAServiceUtils;

    public AnswerResponseDto createAnswer(Long questionId, AnswerRequestDto requestDto) {
        User author = qnAServiceUtils.getCurrentUser();
        Question question = qnAServiceUtils.getQuestionById(questionId);
        qnAServiceUtils.validateStaffAccess();
        Answer saveAnswer = answerJpaRepository.save(new Answer(null, requestDto.content(), author, question));
        return AnswerResponseDto.from(saveAnswer);
    }

    public AnswerResponseDto updateAnswer(Long answerId, AnswerRequestDto requestDto) {
        Answer existingAnswer = qnAServiceUtils.getAnswerById(answerId);
        qnAServiceUtils.validateStaffAccess();
        existingAnswer.setContent(requestDto.content());
        Answer updateAnswer = answerJpaRepository.save(existingAnswer);
        return AnswerResponseDto.from(updateAnswer);
    }

    public void deleteAnswer(Long answerId) {
        Answer existingAnswer = qnAServiceUtils.getAnswerById(answerId);
        qnAServiceUtils.validateStaffAccess();
        answerJpaRepository.delete(existingAnswer);
    }
}
