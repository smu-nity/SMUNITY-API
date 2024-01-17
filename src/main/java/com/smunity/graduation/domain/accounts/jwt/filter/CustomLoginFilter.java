package com.smunity.graduation.domain.accounts.jwt.filter;

import static com.smunity.graduation.domain.accounts.jwt.util.JsonUtil.*;
import static com.smunity.graduation.domain.accounts.jwt.util.ResponseUtil.*;
import static org.springframework.http.HttpStatus.*;

import java.io.IOException;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.smunity.graduation.domain.accounts.jwt.dto.JwtDto;
import com.smunity.graduation.domain.accounts.jwt.userdetails.CustomUserDetails;
import com.smunity.graduation.domain.accounts.jwt.util.JwtUtil;
import com.smunity.graduation.global.common.ApiResponse;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class CustomLoginFilter extends UsernamePasswordAuthenticationFilter {

	private final AuthenticationManager authenticationManager;
	private final JwtUtil jwtUtil;

	private static void setFailureResponse(
		@NonNull HttpServletResponse response,
		@NonNull String errorMessage) throws
		IOException {
		response.setContentType("application/json;charset=UTF-8");
		response.setStatus(401);

		response.getWriter().print(
			ApiResponse.onFailure(
				String.valueOf(HttpStatus.UNAUTHORIZED.value()),
				HttpStatus.UNAUTHORIZED.name(),
				errorMessage
			).toJsonString()
		);

		closeWriter(response);
	}

	private static void setSuccessResponse(
		@NonNull HttpServletResponse response,
		@NonNull JwtDto jwtDto) throws IOException {
		response.setContentType("application/json;charset=UTF-8");
		response.setStatus(200);

		response.getWriter().print(
			ApiResponse.onSuccess(jwtDto).toJsonString()
		);

		closeWriter(response);
	}

	private static void closeWriter(HttpServletResponse response) throws IOException {
		response.getWriter().flush();
		response.getWriter().close();
	}

	@Override
	public Authentication attemptAuthentication(
		@NonNull HttpServletRequest request,
		@NonNull HttpServletResponse response
	) throws AuthenticationException {
		log.info("[*] Login Filter");

		Map<String, Object> requestBody;
		try {
			requestBody = getBody(request);
		} catch (IOException e) {
			try {
				// TODO responseUtil 안 쓰고 Apiresponse로 refactor 하기
				setErrorResponse(response, BAD_REQUEST);
				return null;
			} catch (IOException ex) {
				return null;
			}
		}

		log.info("[*] Request Body : " + requestBody);

		String username = (String)requestBody.get("username");
		String password = (String)requestBody.get("password");

		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password,
			null);
		
		return authenticationManager.authenticate(authToken);
	}

	@Override
	protected void successfulAuthentication(
		@NonNull HttpServletRequest request,
		@NonNull HttpServletResponse response,
		@NonNull FilterChain chain,
		@NonNull Authentication authentication) throws IOException {
		log.info("[*] Login Success");

		CustomUserDetails customUserDetails = (CustomUserDetails)authentication.getPrincipal();

		log.info("[*] Login with " + customUserDetails.getUsername());

		JwtDto jwtDto = new JwtDto(
			jwtUtil.createJwtAccessToken(customUserDetails),
			jwtUtil.createJwtRefreshToken(customUserDetails)
		);

		setSuccessResponse(response, jwtDto);
	}

	@Override
	protected void unsuccessfulAuthentication(
		@NonNull HttpServletRequest request,
		@NonNull HttpServletResponse response,
		@NonNull AuthenticationException failed) throws IOException {
		log.info("[*] Login Fail");

		String errorMessage;
		if (failed instanceof BadCredentialsException) {
			errorMessage = "Bad credentials";
		} else if (failed instanceof LockedException) {
			errorMessage = "Account is locked";
		} else if (failed instanceof DisabledException) {
			errorMessage = "Account is disabled";
		} else if (failed instanceof UsernameNotFoundException) {
			errorMessage = "Account not found";
		} else {
			errorMessage = "Authentication failed";
		}

		setFailureResponse(response, errorMessage);
	}
}