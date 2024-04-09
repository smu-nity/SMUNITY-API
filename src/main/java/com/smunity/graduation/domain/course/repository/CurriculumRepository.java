package com.smunity.graduation.domain.course.repository;

import com.smunity.graduation.domain.accounts.entity.Year;
import com.smunity.graduation.domain.course.entity.Curriculum;
import com.smunity.graduation.global.common.type.Domain;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CurriculumRepository extends JpaRepository<Curriculum, Long> {

    List<Curriculum> findAllByYearAndDomain(Year year, Domain domain);
}
