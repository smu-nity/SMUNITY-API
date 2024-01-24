package com.smunity.graduation.domain.graduation.repository;

import com.smunity.graduation.domain.graduation.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {

}
