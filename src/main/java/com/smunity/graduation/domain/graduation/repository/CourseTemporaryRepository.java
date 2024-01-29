package com.smunity.graduation.domain.graduation.repository;

import com.smunity.graduation.domain.graduation.temporary.CourseTemporary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseTemporaryRepository extends JpaRepository<CourseTemporary, Long> {

    List<CourseTemporary> findAllByUser_Id(Long id);
}
