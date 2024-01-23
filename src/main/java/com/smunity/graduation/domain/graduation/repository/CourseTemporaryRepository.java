package com.smunity.graduation.domain.graduation.repository;

import com.smunity.graduation.domain.graduation.temporary.CourseTemporary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseTemporaryRepository extends JpaRepository<CourseTemporary, Long> {

    List<CourseTemporary> findAllByUser_Id(Long id);
}
