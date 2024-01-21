package com.smunity.graduation.domain.smu.service;

import com.smunity.graduation.domain.smu.dto.AuthRequestDto;
import com.smunity.graduation.domain.smu.dto.CourseResponseDto;
import com.smunity.graduation.domain.smu.exception.AuthExceptionHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final WebClient webClient;

    public ResponseEntity<List<CourseResponseDto>> uploadCourses(AuthRequestDto requestDto) {
        return webClient.post()
                .uri("/api/courses")
                .bodyValue(requestDto)
                .retrieve()
                .onStatus(HttpStatusCode::isError, AuthExceptionHandler::handleError)
                .toEntityList(CourseResponseDto.class)
                .block();
    }
}
