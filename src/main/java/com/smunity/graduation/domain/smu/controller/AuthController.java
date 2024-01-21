package com.smunity.graduation.domain.smu.controller;

import com.smunity.graduation.domain.smu.dto.AuthRequestDto;
import com.smunity.graduation.domain.smu.dto.AuthResponseDto;
import com.smunity.graduation.domain.smu.service.AuthService;
import com.smunity.graduation.global.common.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping
    public ApiResponse<AuthResponseDto> authenticate(@RequestBody @Valid AuthRequestDto requestDto) {
        return ApiResponse.onSuccess(authService.authenticate(requestDto));
    }
}
