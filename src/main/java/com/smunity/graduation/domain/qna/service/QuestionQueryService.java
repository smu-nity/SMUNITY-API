package com.smunity.graduation.domain.qna.service;

import com.smunity.graduation.domain.qna.dto.QuestionListResponseDto;
import com.smunity.graduation.domain.qna.entity.Question;
import com.smunity.graduation.domain.qna.repository.QuestionJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class QuestionQueryService {
    private final QuestionJpaRepository questionJpaRepository;

    public List<QuestionListResponseDto> getQuestionList() {
        List<Question> questions = questionJpaRepository.findAll();
        return QuestionListResponseDto.from(questions);
    }

}
