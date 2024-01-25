package com.smunity.graduation.domain.graduation.repository;

import com.smunity.graduation.domain.graduation.entity.Major;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MajorRepository extends JpaRepository<Major, Long> {
}
