package com.smunity.graduation.domain.smu.exception;

import com.smunity.graduation.global.common.code.BaseErrorCode;
import com.smunity.graduation.global.common.code.status.ErrorCode;
import com.smunity.graduation.global.common.exception.GeneralException;
import org.springframework.web.reactive.function.client.ClientResponse;
import reactor.core.publisher.Mono;

public class AuthExceptionHandler {
    public static Mono<? extends Throwable> handleError(ClientResponse response) {
        BaseErrorCode code = (response.statusCode().value() == 401) ? ErrorCode.SMU_UNAUTHORIZED : ErrorCode.SMU_INTERNAL_SERVER_ERROR;
        return Mono.error(new GeneralException(code));
    }
}
