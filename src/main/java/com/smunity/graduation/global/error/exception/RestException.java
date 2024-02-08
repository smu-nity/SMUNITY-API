package com.smunity.graduation.global.error.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public class RestException extends RuntimeException {
    private final HttpStatus httpStatus;
}
