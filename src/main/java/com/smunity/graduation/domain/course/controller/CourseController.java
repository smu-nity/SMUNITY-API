package com.smunity.graduation.domain.course.controller;

import com.smunity.graduation.domain.auth.dto.AuthCourseResponseDto;
import com.smunity.graduation.domain.auth.dto.AuthRequestDto;
import com.smunity.graduation.domain.auth.service.AuthService;
import com.smunity.graduation.domain.course.dto.CourseResponseDto;
import com.smunity.graduation.domain.course.service.CourseQueryService;
import com.smunity.graduation.domain.course.service.CourseService;
import com.smunity.graduation.global.common.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/courses")
public class CourseController {

    private final AuthService authService;
    private final CourseService courseService;
    private final CourseQueryService courseQueryService;

    @GetMapping
    public ApiResponse<List<CourseResponseDto>> getCourses() {
        // TODO 사용자 인증 적용
        String username = "201911019";
        List<CourseResponseDto> responseDtoList = courseQueryService.getCourses(username);
        return ApiResponse.onSuccess(responseDtoList);
    }

    @PostMapping("/upload")
    public ApiResponse<List<CourseResponseDto>> uploadCourses(@RequestBody @Valid AuthRequestDto requestDto) {
        List<AuthCourseResponseDto> requestDtoList = authService.getCourses(requestDto);
        // TODO 사용자 인증 적용
        String username = "201911019";
        List<CourseResponseDto> responseDtoList = courseService.createCourses(requestDtoList, username);
        return ApiResponse.onSuccess(responseDtoList);
    }
}
