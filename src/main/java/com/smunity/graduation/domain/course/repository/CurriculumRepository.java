package com.smunity.graduation.domain.course.repository;

import com.smunity.graduation.domain.course.entity.Curriculum;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurriculumRepository extends JpaRepository<Curriculum, Long> {
    
}
