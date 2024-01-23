package com.smunity.graduation.domain.qna.repository;

import com.smunity.graduation.domain.qna.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface AnswerJpaRepository extends JpaRepository <Answer, Long> {

    Optional<Answer> findByQuestionId(Long questionId);
}
