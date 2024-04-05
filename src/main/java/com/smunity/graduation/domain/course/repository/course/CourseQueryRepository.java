package com.smunity.graduation.domain.course.repository.course;

import com.smunity.graduation.domain.course.entity.Course;
import com.smunity.graduation.global.common.enums.Category;

import java.util.List;

public interface CourseQueryRepository {

    List<Course> findByUsernameAndCategory(String userName, Category category);
}
