package com.smunity.graduation.global.common.exception;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.smunity.graduation.global.common.ApiResponse;
import com.smunity.graduation.global.common.BaseErrorCode;
import com.smunity.graduation.global.common.ErrorCode;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler({Exception.class})
	public ResponseEntity<ApiResponse<String>> handleAllException(Exception e) {
		log.error(">>>>> Internal Server Error : ", e);
		BaseErrorCode errorCode = GlobalErrorCode.INTERNAL_SERVER_ERROR;
		ApiResponse<String> errorResponse = ApiResponse.onFailure(
			errorCode.getCode(),
			errorCode.getMessage(),
			e.getMessage()
		);
		return ResponseEntity.internalServerError().body(errorResponse);
	}

	@ExceptionHandler({CustomException.class})
	public ResponseEntity<ApiResponse<Void>> handleCustomException(CustomException e) {
		log.warn(">>>>> Custom Exception : {}", e.getMessage());
		BaseErrorCode errorCode = e.getErrorCode();
		return ResponseEntity.status(errorCode.getHttpStatus()).body(errorCode.getErrorResponse());
	}

	@ExceptionHandler({DataIntegrityViolationException.class})
	public ApiResponse<Object> handleIntegrityConstraint(DataIntegrityViolationException e) {
		log.warn(">>>>> Data Integrity Violation Exception : {}", e.getMessage());
		// TODO 학번 중복 예외 처리
		BaseErrorCode errorStatus = ErrorCode.SAMNUL_ERROR;
		return ApiResponse.onFailure(
			errorStatus.getCode(),
			errorStatus.getMessage(),
			null
		);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	protected ResponseEntity<ApiResponse<Map<String, String>>> handleMethodArgumentNotValidException(
		MethodArgumentNotValidException ex
	) {
		// 실패한 validation 을 담을 Map
		Map<String, String> failedValidations = new HashMap<>();
		List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
		// fieldErrors 를 순회하며 failedValidations 에 담는다.
		fieldErrors.forEach(error -> failedValidations.put(error.getField(), error.getDefaultMessage()));
		ApiResponse<Map<String, String>> errorResponse = ApiResponse.onFailure(
			GlobalErrorCode.VALIDATION_FAILED.getCode(),
			GlobalErrorCode.VALIDATION_FAILED.getMessage(),
			failedValidations);
		return ResponseEntity.status(ex.getStatusCode()).body(errorResponse);
	}
	//
	// @ExceptionHandler({SecurityCustomException.class})
	// public ResponseEntity<ApiResponse<String>> handleAuthenticationException(Exception e) {
	// 	log.error(">>>>> Security Server Error : ", e);
	// 	BaseErrorCode errorCode = SecurityErrorCode.INTERNAL_TOKEN_SERVER_ERROR;
	// 	ApiResponse<String> errorResponse = ApiResponse.onFailure(
	// 		errorCode.getCode(),
	// 		errorCode.getMessage(),
	// 		e.getMessage()
	// 	);
	// 	return ResponseEntity.internalServerError().body(errorResponse);
	// }
}
