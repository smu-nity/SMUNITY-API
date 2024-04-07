package com.smunity.graduation.domain.course.repository.course;

import com.smunity.graduation.domain.course.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long>, CourseQueryRepository {

    boolean existsByUserUserNameAndNumber(String userName, String number);

    List<Course> findAllByUserUserNameAndSubDomainIsNotNull(String userName);
}
