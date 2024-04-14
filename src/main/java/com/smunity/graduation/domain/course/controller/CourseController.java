package com.smunity.graduation.domain.course.controller;

import com.smunity.graduation.domain.accounts.annotation.AccountResolver;
import com.smunity.graduation.domain.accounts.entity.User;
import com.smunity.graduation.domain.auth.dto.AuthCourseResponseDto;
import com.smunity.graduation.domain.auth.dto.AuthRequestDto;
import com.smunity.graduation.domain.auth.service.AuthService;
import com.smunity.graduation.domain.course.dto.CourseResponseDto;
import com.smunity.graduation.domain.course.dto.CultureResponseDto;
import com.smunity.graduation.domain.course.dto.ResultResponseDto;
import com.smunity.graduation.domain.course.service.CourseQueryService;
import com.smunity.graduation.domain.course.service.CourseService;
import com.smunity.graduation.global.common.ApiResponse;
import com.smunity.graduation.global.common.type.Category;
import com.smunity.graduation.global.common.type.Domain;
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
    public ApiResponse<ResultResponseDto<CourseResponseDto>> getCourses(@AccountResolver User user, @RequestParam(required = false) Category category) {
        ResultResponseDto<CourseResponseDto> responseDto = courseQueryService.getCourses(user.getUserName(), category);
        return ApiResponse.onSuccess(responseDto);
    }

    @PostMapping("/upload")
    public ApiResponse<ResultResponseDto<CourseResponseDto>> uploadCourses(@AccountResolver User user, @RequestBody @Valid AuthRequestDto requestDto) {
        List<AuthCourseResponseDto> requestDtoList = authService.getCourses(requestDto);
        ResultResponseDto<CourseResponseDto> responseDto = courseService.createCourses(requestDtoList, user.getUserName());
        return ApiResponse.onSuccess(responseDto);
    }

    @GetMapping("/cultures/{domain}")
    public ApiResponse<ResultResponseDto<CultureResponseDto>> getCultureCourses(@AccountResolver User user, @PathVariable Domain domain) {
        ResultResponseDto<CultureResponseDto> responseDto = courseQueryService.getCultureCourses(user.getUserName(), domain);
        return ApiResponse.onSuccess(responseDto);
    }
}
