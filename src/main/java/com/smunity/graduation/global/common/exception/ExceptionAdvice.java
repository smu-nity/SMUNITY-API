package com.smunity.graduation.global.common.exception;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.smunity.graduation.global.common.ApiResponse;
import com.smunity.graduation.global.common.code.ErrorReasonDTO;
import com.smunity.graduation.global.common.code.status.ErrorCode;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice(annotations = {RestController.class})
public class ExceptionAdvice extends ResponseEntityExceptionHandler {

	@ExceptionHandler
	public ResponseEntity<Object> validation(ConstraintViolationException e, WebRequest request) {
		String errorMessage = e.getConstraintViolations().stream()
			.map(constraintViolation -> constraintViolation.getMessage())
			.findFirst()
			.orElseThrow(() -> new RuntimeException("ConstraintViolationException 추출 도중 에러 발생"));

		return handleExceptionInternalConstraint(e, ErrorCode.valueOf(errorMessage), HttpHeaders.EMPTY, request);
	}

	// @Valid 통해 MethodArgumentNotValidException 감지
	@Override
	public ResponseEntity<Object> handleMethodArgumentNotValid(
		MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

		Map<String, String> errors = new LinkedHashMap<>();

		ex.getBindingResult().getFieldErrors().stream()
			.forEach(fieldError -> {
				String fieldName = fieldError.getField();
				String errorMessage = Optional.ofNullable(fieldError.getDefaultMessage()).orElse("");
				errors.merge(fieldName, errorMessage,
					(existingErrorMessage, newErrorMessage) -> existingErrorMessage + ", " + newErrorMessage);
			});
		// 필드명 : ex.getBindingResult().getFieldError().getField(), 각 필드에 대한 에러 : ex.getBindingResult().getFieldError().getDefaultMessage()

		return handleExceptionInternalArgs(ex, HttpHeaders.EMPTY, ErrorCode.valueOf("_BAD_REQUEST"), request, errors);
	}

	@ExceptionHandler
	public ResponseEntity<Object> exception(Exception e, WebRequest request) {
		e.printStackTrace();

		return handleExceptionInternalFalse(e, ErrorCode._INTERNAL_SERVER_ERROR, HttpHeaders.EMPTY,
			ErrorCode._INTERNAL_SERVER_ERROR.getHttpStatus(), request, e.getMessage());
	}

	@ExceptionHandler(value = GeneralException.class)
	public ResponseEntity onThrowException(GeneralException generalException, HttpServletRequest request) {
		ErrorReasonDTO errorReasonHttpStatus = generalException.getErrorReasonHttpStatus();
		return handleExceptionInternal(generalException, errorReasonHttpStatus, null, request);
	}

	private ResponseEntity<Object> handleExceptionInternal(Exception e, ErrorReasonDTO reason,
		HttpHeaders headers, HttpServletRequest request) {

		ApiResponse<Object> body = ApiResponse.onFailure(reason.getCode(), reason.getMessage(), null);
		//        e.printStackTrace();

		WebRequest webRequest = new ServletWebRequest(request);
		return super.handleExceptionInternal(
			e,
			body,
			headers,
			reason.getHttpStatus(),
			webRequest
		);
	}

	private ResponseEntity<Object> handleExceptionInternalFalse(Exception e, ErrorCode errorCommonStatus,
		HttpHeaders headers, HttpStatus status, WebRequest request, String errorPoint) {
		ApiResponse<Object> body = ApiResponse.onFailure(errorCommonStatus.getCode(), errorCommonStatus.getMessage(),
			errorPoint);
		return super.handleExceptionInternal(
			e,
			body,
			headers,
			status,
			request
		);
	}

	// @Valid 관련
	private ResponseEntity<Object> handleExceptionInternalArgs(Exception e, HttpHeaders headers,
		ErrorCode errorCommonStatus,
		WebRequest request, Map<String, String> errorArgs) {
		ApiResponse<Object> body = ApiResponse.onFailure(errorCommonStatus.getCode(), errorCommonStatus.getMessage(),
			errorArgs);
		return super.handleExceptionInternal(
			e,
			body,
			headers,
			errorCommonStatus.getHttpStatus(),
			request
		);
	}

	private ResponseEntity<Object> handleExceptionInternalConstraint(Exception e, ErrorCode errorCommonStatus,
		HttpHeaders headers, WebRequest request) {
		ApiResponse<Object> body = ApiResponse.onFailure(errorCommonStatus.getCode(), errorCommonStatus.getMessage(),
			null);
		return super.handleExceptionInternal(
			e,
			body,
			headers,
			errorCommonStatus.getHttpStatus(),
			request
		);
	}
}
