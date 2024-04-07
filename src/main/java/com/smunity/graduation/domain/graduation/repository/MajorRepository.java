package com.smunity.graduation.domain.graduation.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smunity.graduation.domain.graduation.entity.Major;

public interface MajorRepository extends JpaRepository<Major, Long> {
}
