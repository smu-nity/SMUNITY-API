package com.smunity.graduation.domain.smu.controller;

import com.smunity.graduation.domain.smu.dto.AuthRequestDto;
import com.smunity.graduation.domain.smu.dto.CourseRequestDto;
import com.smunity.graduation.domain.smu.service.AuthService;
import com.smunity.graduation.domain.smu.service.CourseService;
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
    public ApiResponse<List<CourseRequestDto>> uploadCourses(@RequestBody @Valid AuthRequestDto requestDto) {
        List<CourseRequestDto> requestDtoList = authService.getCourses(requestDto);
        // TODO return 타입 List<CourseRequestDto> -> List<CourseResponseDto>
        List<CourseRequestDto> responseDtoList = courseService.createCourses(requestDtoList);
        return ApiResponse.onSuccess(responseDtoList);
    }
}
