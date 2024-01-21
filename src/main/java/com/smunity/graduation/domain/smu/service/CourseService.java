package com.smunity.graduation.domain.smu.service;

import com.smunity.graduation.domain.smu.dto.CourseRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseService {
    // TODO return 타입 CourseRequestDto -> CourseResponseDto
    public CourseRequestDto createCourse(CourseRequestDto requestDto) {
        // TODO requestDto -> entity (toEntity)

        // TODO entity save

        // TODO entity -> responseDto (from)

        return requestDto;
    }

    public List<CourseRequestDto> createCourses(List<CourseRequestDto> requestDtoList) {
        return requestDtoList.stream().map(this::createCourse).collect(Collectors.toList());
    }
}
