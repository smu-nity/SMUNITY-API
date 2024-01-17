package com.smunity.graduation.domain.smu.service;

import com.smunity.graduation.domain.smu.dto.AuthRequestDto;
import com.smunity.graduation.domain.smu.dto.AuthResponseDto;
import com.smunity.graduation.domain.smu.dto.CourseResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SmuService {
    private final WebClient webClient;

    public ResponseEntity<AuthResponseDto> getUserInfo(AuthRequestDto requestDto) {
        return webClient.post()
                .uri("/api/userinfo")
                .bodyValue(requestDto)
                .retrieve()
                .toEntity(AuthResponseDto.class)
                .block();
    }

    public ResponseEntity<List<CourseResponseDto>> getCourses(AuthRequestDto requestDto) {
        return webClient.post()
                .uri("/api/courses")
                .bodyValue(requestDto)
                .retrieve()
                .toEntityList(CourseResponseDto.class)
                .block();
    }
}
