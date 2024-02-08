package com.smunity.graduation.domain.auth.service;

import com.smunity.graduation.domain.auth.dto.AuthCourseResponseDto;
import com.smunity.graduation.domain.auth.dto.AuthRequestDto;
import com.smunity.graduation.domain.auth.dto.AuthResponseDto;
import com.smunity.graduation.domain.auth.exception.AuthExceptionHandler;
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

    public List<AuthCourseResponseDto> getCourses(AuthRequestDto requestDto) {
        return webClient.post()
                .uri("/api/courses")
                .bodyValue(requestDto)
                .retrieve()
                .onStatus(HttpStatusCode::isError, AuthExceptionHandler::handleError)
                .toEntityList(AuthCourseResponseDto.class)
                .block()
                .getBody();
    }
}
