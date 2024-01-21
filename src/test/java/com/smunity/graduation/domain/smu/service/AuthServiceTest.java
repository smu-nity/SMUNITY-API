package com.smunity.graduation.domain.smu.service;

import com.smunity.graduation.domain.auth.dto.AuthCourseResponseDto;
import com.smunity.graduation.domain.auth.dto.AuthRequestDto;
import com.smunity.graduation.domain.auth.dto.AuthResponseDto;
import com.smunity.graduation.domain.auth.service.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class AuthServiceTest {
    @Autowired
    private AuthService authService;

    private AuthRequestDto requestDto;

    @BeforeEach
    public void setUp() {
        //given
        requestDto = new AuthRequestDto("201911019", "1q2w3e4r!!!");
    }

    @Test
    public void authenticateTest() throws Exception {
        //when
        AuthResponseDto response = authService.authenticate(requestDto);

        //then
        System.out.println(response);
    }

    @Test
    public void getCoursesTest() throws Exception {
        //when
        List<AuthCourseResponseDto> response = authService.getCourses(requestDto);

        //then
        System.out.println(response);
    }
}
