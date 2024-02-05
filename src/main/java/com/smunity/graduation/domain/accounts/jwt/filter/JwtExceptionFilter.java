package com.smunity.graduation.domain.accounts.jwt.filter;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;

import com.smunity.graduation.domain.accounts.jwt.exception.AccountsExceptionHandler;
import com.smunity.graduation.domain.accounts.jwt.exception.TokenErrorCode;
import com.smunity.graduation.domain.accounts.jwt.util.HttpResponseUtil;
import com.smunity.graduation.global.common.BaseErrorCode;

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
		} catch (AccountsExceptionHandler e) {
			log.info(">>>>> AccountsExceptionHandler : ", e);
			BaseErrorCode errorCode = e.getErrorCode();
			HttpResponseUtil.setErrorResponse(
				response,
				errorCode.getHttpStatus(),
				errorCode.getMessage()
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
