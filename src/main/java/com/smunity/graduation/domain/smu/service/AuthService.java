package com.smunity.graduation.domain.smu.service;

import com.smunity.graduation.domain.smu.dto.AuthRequestDto;
import com.smunity.graduation.domain.smu.dto.AuthResponseDto;
import com.smunity.graduation.domain.smu.dto.CourseRequestDto;
import com.smunity.graduation.domain.smu.exception.AuthExceptionHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final WebClient webClient;

    public AuthResponseDto authenticate(AuthRequestDto requestDto) {
        return webClient.post()
                .uri("/api/userinfo")
                .bodyValue(requestDto)
                .retrieve()
                .onStatus(HttpStatusCode::isError, AuthExceptionHandler::handleError)
                .toEntity(AuthResponseDto.class)
                .block()
                .getBody();
    }

    public List<CourseRequestDto> getCourses(AuthRequestDto requestDto) {
        return webClient.post()
                .uri("/api/courses")
                .bodyValue(requestDto)
                .retrieve()
                .onStatus(HttpStatusCode::isError, AuthExceptionHandler::handleError)
                .toEntityList(CourseRequestDto.class)
                .block()
                .getBody();
    }
}
