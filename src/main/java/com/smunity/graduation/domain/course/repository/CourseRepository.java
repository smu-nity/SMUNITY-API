package com.smunity.graduation.domain.course.repository;

import com.smunity.graduation.domain.course.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {

    boolean existsByUserUserNameAndNumber(String userName, String number);

    List<Course> findAllByUserUserName(String userName);
}
