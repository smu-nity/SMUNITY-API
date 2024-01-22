package com.smunity.graduation.domain.course.repository;

import com.smunity.graduation.domain.course.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
}
