package com.smunity.graduation.domain.course.controller;

import com.smunity.graduation.domain.accounts.annotation.AccountResolver;
import com.smunity.graduation.domain.accounts.entity.User;
import com.smunity.graduation.domain.auth.dto.AuthCourseResponseDto;
import com.smunity.graduation.domain.auth.dto.AuthRequestDto;
import com.smunity.graduation.domain.auth.service.AuthService;
import com.smunity.graduation.domain.course.dto.ResultResponseDto;
import com.smunity.graduation.domain.course.service.CourseQueryService;
import com.smunity.graduation.domain.course.service.CourseService;
import com.smunity.graduation.global.common.ApiResponse;
import com.smunity.graduation.global.common.enums.Category;
import com.smunity.graduation.global.common.enums.Domain;
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
    public ApiResponse<ResultResponseDto> getCourses(@AccountResolver User user, @RequestParam(required = false) Category category) {
        ResultResponseDto responseDtoList = courseQueryService.getCourses(user.getUserName(), category);
        return ApiResponse.onSuccess(responseDtoList);
    }

    @PostMapping("/upload")
    public ApiResponse<ResultResponseDto> uploadCourses(@AccountResolver User user, @RequestBody @Valid AuthRequestDto requestDto) {
        List<AuthCourseResponseDto> requestDtoList = authService.getCourses(requestDto);
        ResultResponseDto responseDtoList = courseService.createCourses(requestDtoList, user.getUserName());
        return ApiResponse.onSuccess(responseDtoList);
    }

    @GetMapping("/cultures/{domain}")
    public ApiResponse<ResultResponseDto> getCultureCourses(@AccountResolver User user, @PathVariable Domain domain) {
        ResultResponseDto responseDtoList = courseQueryService.getCultureCourses(user.getUserName(), domain);
        return ApiResponse.onSuccess(responseDtoList);
    }
}
