package com.smunity.graduation.domain.smu.controller;

import com.smunity.graduation.domain.smu.dto.AuthRequestDto;
import com.smunity.graduation.domain.smu.dto.AuthResponseDto;
import com.smunity.graduation.domain.smu.dto.CourseResponseDto;
import com.smunity.graduation.domain.smu.service.SmuService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/smu")
public class SmuController {
    private final SmuService smuService;

    @PostMapping("/auth")
    public ResponseEntity<AuthResponseDto> authenticate(@RequestBody @Valid AuthRequestDto requestDto) {
        return smuService.getUserInfo(requestDto);
    }

    @PostMapping("/courses")
    public ResponseEntity<List<CourseResponseDto>> getCourses(@RequestBody @Valid AuthRequestDto requestDto) {
        return smuService.getCourses(requestDto);
    }
}
