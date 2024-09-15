package com.smunity.graduation.domain.accounts.jwt.filter;

import com.smunity.graduation.domain.accounts.jwt.exception.SecurityCustomException;
import com.smunity.graduation.domain.accounts.jwt.exception.TokenErrorCode;
import com.smunity.graduation.domain.accounts.jwt.util.HttpResponseUtil;
import com.smunity.graduation.global.common.dto.ErrorResponse;
import com.smunity.graduation.global.exception.code.BaseErrorCode;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
public class JwtExceptionFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (SecurityCustomException e) {
            log.warn(">>>>> SecurityCustomException : ", e);
            BaseErrorCode errorCode = e.getErrorCode();
            ErrorResponse<Object> errorResponse = ErrorResponse.from(errorCode);
            
            HttpResponseUtil.setErrorResponse(
                    response,
                    errorCode.getHttpStatus(),
                    errorResponse
            );
        } catch (Exception e) {
            log.error(">>>>> Exception : ", e);
            TokenErrorCode errorCode = TokenErrorCode.INTERNAL_SECURITY_ERROR;
            ErrorResponse<Object> errorResponse = ErrorResponse.from(errorCode);

            HttpResponseUtil.setErrorResponse(
                    response,
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    errorResponse
            );
        }
    }
}
