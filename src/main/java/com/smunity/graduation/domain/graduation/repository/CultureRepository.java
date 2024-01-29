package com.smunity.graduation.domain.graduation.repository;

import com.smunity.graduation.domain.graduation.entity.Culture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CultureRepository extends JpaRepository<Culture, Long> {
}
