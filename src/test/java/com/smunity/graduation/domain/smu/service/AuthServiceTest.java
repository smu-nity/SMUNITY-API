package com.smunity.graduation.domain.smu.service;

import com.smunity.graduation.domain.smu.dto.AuthRequestDto;
import com.smunity.graduation.domain.smu.dto.AuthResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

@SpringBootTest
public class AuthServiceTest {
    @Autowired
    private AuthService authService;

    @Test
    public void authenticateTest() throws Exception {
        //given
        AuthRequestDto requestDto = new AuthRequestDto("201911019", "password");

        //when
        ResponseEntity<AuthResponseDto> response = authService.authenticate(requestDto);

        //then
        System.out.println(response.getBody());
    }
}
