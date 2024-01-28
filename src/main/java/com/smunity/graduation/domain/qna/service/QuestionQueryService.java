package com.smunity.graduation.domain.qna.service;

import com.smunity.graduation.domain.qna.dto.QuestionsResponseDto;
import com.smunity.graduation.domain.qna.entity.Question;
import com.smunity.graduation.domain.qna.repository.QuestionJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class QuestionQueryService {
    private final QuestionJpaRepository questionJpaRepository;

    public Page<QuestionsResponseDto> getQuestionList(Pageable pageable) {
        Page<Question> questions = questionJpaRepository.findAll(pageable);
        return questions.map(QuestionsResponseDto::from);
    }

}
