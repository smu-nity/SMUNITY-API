package com.smunity.graduation.domain.smu.service;

import com.smunity.graduation.domain.smu.dto.AuthRequestDto;
import com.smunity.graduation.domain.smu.dto.AuthResponseDto;
import com.smunity.graduation.domain.smu.dto.CourseResponseDto;
import com.smunity.graduation.global.error.exception.RestException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

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
                .onStatus(HttpStatusCode::isError, this::handleError)
                .toEntity(AuthResponseDto.class)
                .block();
    }

    public ResponseEntity<List<CourseResponseDto>> getCourses(AuthRequestDto requestDto) {
        return webClient.post()
                .uri("/api/courses")
                .bodyValue(requestDto)
                .retrieve()
                .onStatus(HttpStatusCode::isError, this::handleError)
                .toEntityList(CourseResponseDto.class)
                .block();
    }

    private Mono<? extends Throwable> handleError(ClientResponse response) {
        return Mono.error(new RestException((HttpStatus) response.statusCode()));
    }
}
