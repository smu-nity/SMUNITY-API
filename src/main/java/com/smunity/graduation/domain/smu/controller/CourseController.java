package com.smunity.graduation.domain.smu.controller;

import com.smunity.graduation.domain.smu.dto.AuthRequestDto;
import com.smunity.graduation.domain.smu.dto.CourseResponseDto;
import com.smunity.graduation.domain.smu.service.CourseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/courses")
public class CourseController {
    private final CourseService courseService;

    @PostMapping("/upload")
    public ResponseEntity<List<CourseResponseDto>> uploadCourses(@RequestBody @Valid AuthRequestDto requestDto) {
        return courseService.uploadCourses(requestDto);
    }
}
