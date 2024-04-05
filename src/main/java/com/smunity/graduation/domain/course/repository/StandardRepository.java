package com.smunity.graduation.domain.course.repository;

import com.smunity.graduation.domain.accounts.entity.Year;
import com.smunity.graduation.domain.course.entity.Standard;
import com.smunity.graduation.global.common.enums.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StandardRepository extends JpaRepository<Standard, Long> {

    Optional<Standard> findByYearAndCategory(Year year, Category category);
}
