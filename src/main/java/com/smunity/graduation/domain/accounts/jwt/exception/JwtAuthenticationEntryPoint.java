package com.smunity.graduation.domain.accounts.jwt.exception;

import com.smunity.graduation.domain.accounts.jwt.util.HttpResponseUtil;
import com.smunity.graduation.global.common.dto.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException)
            throws IOException {
        log.error(">>>>>> AuthenticationException: ", authException);
        TokenErrorCode errorCode = TokenErrorCode.UNAUTHORIZED;
        HttpResponseUtil.setErrorResponse(response, errorCode.getHttpStatus(), ErrorResponse.from(errorCode));
    }
}
