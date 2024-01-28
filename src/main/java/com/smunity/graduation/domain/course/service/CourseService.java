package com.smunity.graduation.domain.course.service;

import com.smunity.graduation.domain.auth.dto.AuthCourseResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseService {
    
    // TODO return 타입 AuthCourseResponseDto -> CourseResponseDto
    public AuthCourseResponseDto createCourse(AuthCourseResponseDto requestDto) {
        // TODO requestDto -> entity (toEntity)

        // TODO entity save

        // TODO entity -> responseDto (from)

        return requestDto;
    }

    public List<AuthCourseResponseDto> createCourses(List<AuthCourseResponseDto> requestDtoList) {
        return requestDtoList.stream().map(this::createCourse).collect(Collectors.toList());
    }
}
