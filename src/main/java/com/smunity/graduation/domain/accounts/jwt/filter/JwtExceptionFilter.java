package com.smunity.graduation.domain.accounts.jwt.filter;

import static com.smunity.graduation.domain.accounts.jwt.exception.status.TokenErrorCode.*;
import static com.smunity.graduation.domain.accounts.jwt.util.ResponseUtil.*;
import static org.springframework.http.HttpStatus.*;

import java.io.IOException;

import org.springframework.web.filter.OncePerRequestFilter;

import com.smunity.graduation.domain.accounts.jwt.exception.CustomExpiredJwtException;
import com.smunity.graduation.domain.accounts.jwt.exception.CustomMalformedException;
import com.smunity.graduation.domain.accounts.jwt.exception.CustomNoTokenException;
import com.smunity.graduation.domain.accounts.jwt.exception.CustomSignatureException;
import com.smunity.graduation.global.common.ApiResponse;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
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
		throws ServletException, IOException {
		try {
			filterChain.doFilter(request, response);
		} catch (CustomExpiredJwtException e) {
			log.warn(e.getMessage());
			setErrorResponse(response, TOKEN_EXPIRED.getHttpStatus());
		} catch (CustomMalformedException e) {
			log.warn(e.getMessage());
			setErrorResponse(response, INVALID_FORM_TOKEN.getHttpStatus());
		} catch (CustomNoTokenException e) {
			log.warn(e.getMessage());
			setErrorResponse(response, NO_TOKEN.getHttpStatus());
		} catch (CustomSignatureException e) {
			log.warn(e.getMessage());
			setErrorResponse(response, SIGNATURE_ERROR.getHttpStatus());
		} catch (Exception e) {
			log.warn(e.getMessage());
			log.warn(">>>>> Internal Server Error : ", e);

			response.getWriter().print(
				ApiResponse.onFailure(
					INTERNAL_SERVER_ERROR.name(),
					INTERNAL_SERVER_ERROR.getReasonPhrase(),
					e.getMessage()
				).toJsonString()
			);
		}
	}
}
