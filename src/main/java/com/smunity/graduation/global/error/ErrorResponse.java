package com.smunity.graduation.global.error;

import lombok.Builder;
import org.springframework.http.HttpStatus;

@Builder
public record ErrorResponse(
        int value,
        String reasonPhrase
) {
    public static ErrorResponse from(HttpStatus httpStatus) {
        return ErrorResponse.builder()
                .value(httpStatus.value())
                .reasonPhrase(httpStatus.getReasonPhrase())
                .build();
    }
}
