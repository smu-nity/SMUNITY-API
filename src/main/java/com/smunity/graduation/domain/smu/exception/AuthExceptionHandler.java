package com.smunity.graduation.domain.smu.exception;

import com.smunity.graduation.global.error.exception.RestException;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.ClientResponse;
import reactor.core.publisher.Mono;

public class AuthExceptionHandler {
    public static Mono<? extends Throwable> handleError(ClientResponse response) {
        return Mono.error(new RestException((HttpStatus) response.statusCode()));
    }
}
