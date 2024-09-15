package com.smunity.graduation.global.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.smunity.graduation.global.exception.code.BaseErrorCode;
import lombok.NonNull;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public record ErrorResponse<T>(
        @NonNull
        String code,

        @NonNull
        String message,

        @JsonInclude(JsonInclude.Include.NON_NULL)
        T detail
) {

    public static ResponseEntity<ErrorResponse<Void>> handle(BaseErrorCode errorCode) {
        return ResponseEntity.status(errorCode.getHttpStatus()).body(from(errorCode));
    }

    public static ResponseEntity<ErrorResponse<Map<String, String>>> handle(BaseErrorCode errorCode, List<FieldError> fieldErrors) {
        return ResponseEntity.status(errorCode.getHttpStatus()).body(of(errorCode, fieldErrors));
    }

    public static <T> ErrorResponse<T> from(BaseErrorCode errorCode) {
        return new ErrorResponse<>(errorCode.getCode(), errorCode.getMessage(), null);
    }

    public static <T> ErrorResponse<T> of(String code, String message) {
        return new ErrorResponse<>(code, message, null);
    }

    private static ErrorResponse<Map<String, String>> of(BaseErrorCode errorCode, List<FieldError> fieldErrors) {
        return new ErrorResponse<>(errorCode.getCode(), errorCode.getMessage(), convertErrors(fieldErrors));
    }

    private static Map<String, String> convertErrors(List<FieldError> fieldErrors) {
        return fieldErrors.stream().collect(
                Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage)
        );
    }

    public String toJsonString() throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(this);
    }
}
