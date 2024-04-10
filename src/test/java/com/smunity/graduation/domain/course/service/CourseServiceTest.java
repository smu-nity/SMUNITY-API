package com.smunity.graduation.domain.course.service;

import com.smunity.graduation.domain.auth.dto.AuthCourseResponseDto;
import com.smunity.graduation.domain.auth.dto.AuthRequestDto;
import com.smunity.graduation.domain.auth.service.AuthService;
import com.smunity.graduation.domain.course.dto.CourseResponseDto;
import com.smunity.graduation.domain.course.dto.ResultResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class CourseServiceTest {

    @Autowired
    AuthService authService;

    @Autowired
    CourseService courseService;

    private AuthRequestDto requestDto;

    @BeforeEach
    public void setUp() {
        //given
        requestDto = new AuthRequestDto("201911019", "1q2w3e4r!");
    }

    @Test
    public void createCourses() throws Exception {
        //when
        List<AuthCourseResponseDto> requestDtoList = authService.getCourses(requestDto);
        ResultResponseDto<CourseResponseDto> responseDto = courseService.createCourses(requestDtoList, requestDto.username());

        //then
        System.out.println(responseDto);
    }
}
