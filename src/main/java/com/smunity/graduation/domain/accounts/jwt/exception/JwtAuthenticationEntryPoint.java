package com.smunity.graduation.domain.accounts.jwt.exception;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.smunity.graduation.domain.accounts.jwt.util.HttpResponseUtil;
import com.smunity.graduation.global.common.ApiResponse;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
		AuthenticationException authException) throws IOException {

		log.info("[*] AuthenticationException: ", authException);

		ApiResponse<String> errorResponse = ApiResponse.onFailure(TokenErrorCode.UNAUTHORIZED.getCode(),
			TokenErrorCode.FORBIDDEN.getMessage(), authException.getMessage());

		HttpResponseUtil.setErrorResponse(response, HttpStatus.UNAUTHORIZED, errorResponse);
	}
}
