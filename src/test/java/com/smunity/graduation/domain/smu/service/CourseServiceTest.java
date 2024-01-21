package com.smunity.graduation.domain.smu.service;

import com.smunity.graduation.domain.smu.dto.AuthRequestDto;
import com.smunity.graduation.domain.smu.dto.CourseResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.List;

@SpringBootTest
public class CourseServiceTest {
    @Autowired
    private CourseService courseService;

    @Test
    public void updateCoursesTest() throws Exception {
        //given
        AuthRequestDto requestDto = new AuthRequestDto("201911019", "password");

        //when
        ResponseEntity<List<CourseResponseDto>> response = courseService.uploadCourses(requestDto);

        //then
        System.out.println(response.getBody());
    }
}
