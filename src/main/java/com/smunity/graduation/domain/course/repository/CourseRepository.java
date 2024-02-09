package com.smunity.graduation.domain.course.repository;

import com.smunity.graduation.domain.course.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, Long> {

    Optional<Course> findByUserUserNameAndNumber(String userName, String number);

    List<Course> findAllByUserUserName(String userName);
}
