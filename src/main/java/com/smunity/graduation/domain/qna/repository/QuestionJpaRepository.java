package com.smunity.graduation.domain.qna.repository;

import com.smunity.graduation.domain.qna.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionJpaRepository extends JpaRepository <Question, Long> {
}
