package com.smunity.graduation.global.exception.code;

import com.smunity.graduation.global.common.dto.ApiResponse;
import org.springframework.http.HttpStatus;

public interface BaseErrorCode {

	HttpStatus getHttpStatus();

	String getCode();

	String getMessage();

	ApiResponse<Void> getErrorResponse();
}
