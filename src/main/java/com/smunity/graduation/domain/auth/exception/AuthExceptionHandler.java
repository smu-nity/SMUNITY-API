package com.smunity.graduation.domain.auth.exception;

import org.springframework.web.reactive.function.client.ClientResponse;

import com.smunity.graduation.global.common.BaseErrorCode;
import com.smunity.graduation.global.common.ErrorCode;
import com.smunity.graduation.global.common.exception.CustomException;

import reactor.core.publisher.Mono;

public class AuthExceptionHandler {

	public static Mono<? extends Throwable> handleError(ClientResponse response) {
		BaseErrorCode code =
			(response.statusCode().value() == 401) ? ErrorCode.AUTH_UNAUTHORIZED : ErrorCode.AUTH_INTERNAL_SERVER_ERROR;
		return Mono.error(new CustomException(code));
	}
}
