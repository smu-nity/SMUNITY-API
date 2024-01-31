package com.smunity.graduation.domain.qna.service;

import com.smunity.graduation.domain.accounts.entity.User;
import com.smunity.graduation.domain.accounts.exception.AccountsExceptionHandler;
import com.smunity.graduation.domain.accounts.repository.user.UserRepository;
import com.smunity.graduation.domain.qna.dto.AnswerRequestDto;
import com.smunity.graduation.domain.qna.dto.AnswerResponseDto;
import com.smunity.graduation.domain.qna.entity.Answer;
import com.smunity.graduation.domain.qna.entity.Question;
import com.smunity.graduation.domain.qna.repository.AnswerJpaRepository;
import com.smunity.graduation.global.common.code.status.ErrorCode;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Transactional
@Service
public class AnswerService {

    private final AnswerJpaRepository answerJpaRepository;
    private final UserRepository userRepository;
    private final QnAServiceUtils qnAServiceUtils;

    public AnswerResponseDto createAnswer(Long questionId, AnswerRequestDto requestDto) {
        User author = userRepository.findByUserName("admin")
                .orElseThrow(() -> new AccountsExceptionHandler(ErrorCode.USER_NOT_FOUND));

        Question question = qnAServiceUtils.getQuestionById(questionId);
        Answer saveAnswer = answerJpaRepository.save(new Answer(null, requestDto.content(), author, question));
        return AnswerResponseDto.from(saveAnswer);
    }

    public AnswerResponseDto updateAnswer(Long answerId, AnswerRequestDto requestDto) {
        Answer existingAnswer = qnAServiceUtils.getAnswerById(answerId);
        existingAnswer.setContent(requestDto.content());
        Answer updateAnswer = answerJpaRepository.save(existingAnswer);
        return AnswerResponseDto.from(updateAnswer);
    }

    public void deleteAnswer(Long answerId) {
        Answer existingAnswer = qnAServiceUtils.getAnswerById(answerId);
        answerJpaRepository.delete(existingAnswer);
    }
}
