package com.smunity.graduation.domain.accounts.jwt.filter;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;

import com.smunity.graduation.domain.accounts.jwt.exception.SecurityFilterException;
import com.smunity.graduation.domain.accounts.jwt.exception.status.TokenErrorCode;
import com.smunity.graduation.domain.accounts.jwt.util.HttpResponseUtil;
import com.smunity.graduation.global.common.code.BaseErrorCode;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JwtExceptionFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(
		@NonNull HttpServletRequest request,
		@NonNull HttpServletResponse response,
		@NonNull FilterChain filterChain)
		throws IOException {
		try {
			filterChain.doFilter(request, response);
		} catch (SecurityFilterException e) {
			log.info(">>>>> SecurityCustomException : ", e);
			BaseErrorCode errorCode = e.getCode();
			HttpResponseUtil.setErrorResponse(
				response,
				errorCode.getReasonHttpStatus().getHttpStatus(),
				errorCode.getReason().getMessage()
			);

		} catch (Exception e) {
			log.info(">>>>> Exception : ", e);
			HttpResponseUtil.setErrorResponse(
				response,
				HttpStatus.INTERNAL_SERVER_ERROR,
				TokenErrorCode.INTERNAL_SECURITY_ERROR.getMessage()
			);
		}
	}
}
