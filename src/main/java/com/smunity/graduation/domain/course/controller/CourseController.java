package com.smunity.graduation.domain.course.controller;

import com.smunity.graduation.domain.accounts.annotation.AccountResolver;
import com.smunity.graduation.domain.accounts.entity.User;
import com.smunity.graduation.domain.auth.dto.AuthCourseResponseDto;
import com.smunity.graduation.domain.auth.dto.AuthRequestDto;
import com.smunity.graduation.domain.auth.service.AuthService;
import com.smunity.graduation.domain.course.dto.CourseResponseDto;
import com.smunity.graduation.domain.course.dto.CreditResponseDto;
import com.smunity.graduation.domain.course.dto.CultureResponseDto;
import com.smunity.graduation.domain.course.dto.ResultResponseDto;
import com.smunity.graduation.domain.course.service.CourseQueryService;
import com.smunity.graduation.domain.course.service.CourseService;
import com.smunity.graduation.global.common.type.Category;
import com.smunity.graduation.global.common.type.Domain;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<ResultResponseDto<CourseResponseDto>> getCourses(@AccountResolver User user, @RequestParam(required = false) Category category) {
        ResultResponseDto<CourseResponseDto> responseDto = courseQueryService.getCourses(user.getUserName(), category);
        return ResponseEntity.ok(responseDto);
    }

    @PostMapping("/upload")
    public ResponseEntity<ResultResponseDto<CourseResponseDto>> uploadCourses(@AccountResolver User user, @RequestBody @Valid AuthRequestDto requestDto) {
        List<AuthCourseResponseDto> requestDtoList = authService.getCourses(requestDto);
        ResultResponseDto<CourseResponseDto> responseDto = courseService.createCourses(requestDtoList, user.getUserName());
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @GetMapping("/credit")
    public ResponseEntity<CreditResponseDto> getCoursesCredit(@AccountResolver User user) {
        CreditResponseDto responseDto = courseQueryService.getCoursesCredit(user.getUserName());
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/cultures/{domain}")
    public ResponseEntity<ResultResponseDto<CultureResponseDto>> getCultureCourses(@AccountResolver User user, @PathVariable Domain domain) {
        ResultResponseDto<CultureResponseDto> responseDto = courseQueryService.getCultureCourses(user.getUserName(), domain);
        return ResponseEntity.ok(responseDto);
    }
}
