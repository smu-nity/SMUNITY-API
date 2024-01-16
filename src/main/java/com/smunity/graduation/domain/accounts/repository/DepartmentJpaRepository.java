package com.smunity.graduation.domain.accounts.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smunity.graduation.domain.accounts.entity.Department;

public interface DepartmentJpaRepository extends JpaRepository<Department, Long> {
	Optional<Department> findByName(String name);
}
