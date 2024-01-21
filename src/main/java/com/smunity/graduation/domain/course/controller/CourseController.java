package com.smunity.graduation.domain.course.controller;

import com.smunity.graduation.domain.auth.dto.AuthCourseResponseDto;
import com.smunity.graduation.domain.auth.dto.AuthRequestDto;
import com.smunity.graduation.domain.auth.service.AuthService;
import com.smunity.graduation.domain.course.service.CourseService;
import com.smunity.graduation.global.common.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/courses")
public class CourseController {
    private final AuthService authService;
    private final CourseService courseService;

    @PostMapping("/upload")
    public ApiResponse<List<AuthCourseResponseDto>> uploadCourses(@RequestBody @Valid AuthRequestDto requestDto) {
        List<AuthCourseResponseDto> requestDtoList = authService.getCourses(requestDto);
        // TODO return 타입 List<AuthCourseResponseDto> -> List<CourseResponseDto>
        List<AuthCourseResponseDto> responseDtoList = courseService.createCourses(requestDtoList);
        return ApiResponse.onSuccess(responseDtoList);
    }
}
