package com.smunity.graduation.domain.course.service;

import com.smunity.graduation.domain.course.dto.CourseResponseDto;
import com.smunity.graduation.domain.course.entity.Course;
import com.smunity.graduation.domain.course.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CourseQueryService {

    private final CourseRepository courseRepository;

    // TODO 사용자 인증 적용
    public List<CourseResponseDto> getCourses(String username) {
        List<Course> courses = courseRepository.findAllByUserUserName(username);
        return CourseResponseDto.from(courses);
    }
}
