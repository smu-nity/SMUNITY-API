package com.smunity.graduation.domain.smu.service;

import com.smunity.graduation.domain.smu.dto.AuthRequestDto;
import com.smunity.graduation.domain.smu.dto.AuthResponseDto;
import com.smunity.graduation.domain.smu.dto.CourseResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.List;

@SpringBootTest
public class SmuServiceTest {
    @Autowired
    private AuthService authService;

    @Test
    public void getUserInfoTest() throws Exception {
        //given
        AuthRequestDto requestDto = new AuthRequestDto("201911019", "password");

        //when
        ResponseEntity<AuthResponseDto> response = authService.authenticate(requestDto);

        //then
        System.out.println(response.getBody());
    }

    @Test
    public void getCoursesTest() throws Exception {
        //given
        AuthRequestDto requestDto = new AuthRequestDto("201911019", "password");

        //when
        ResponseEntity<List<CourseResponseDto>> response = authService.getCourses(requestDto);

        //then
        System.out.println(response.getBody());
    }
}
