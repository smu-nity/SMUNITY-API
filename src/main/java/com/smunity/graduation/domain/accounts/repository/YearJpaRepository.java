package com.smunity.graduation.domain.accounts.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smunity.graduation.domain.accounts.entity.Year;

public interface YearJpaRepository extends JpaRepository<Year, Long> {

	Optional<Year> findByYear(String year);
}
