package com.smunity.graduation.domain.accounts.jwt.exception;

import com.smunity.graduation.domain.accounts.jwt.util.HttpResponseUtil;
import com.smunity.graduation.global.common.dto.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException {
        log.warn("Access Denied: ", accessDeniedException);
        TokenErrorCode errorCode = TokenErrorCode.FORBIDDEN;
        HttpResponseUtil.setErrorResponse(response, errorCode.getHttpStatus(), ErrorResponse.from(errorCode));
    }
}
