package com.smunity.graduation.domain.course.service;

import com.smunity.graduation.domain.course.dto.CourseResponseDto;
import com.smunity.graduation.domain.course.entity.Course;
import com.smunity.graduation.domain.course.repository.course.CourseRepository;
import com.smunity.graduation.global.common.enums.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CourseQueryService {

    private final CourseRepository courseRepository;

    public List<CourseResponseDto> getCourses(String username, Category category) {
        List<Course> courses = courseRepository.findByUsernameAndCategory(username, category);
        return CourseResponseDto.from(courses);
    }
}
