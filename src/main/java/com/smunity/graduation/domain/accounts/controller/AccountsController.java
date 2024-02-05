package com.smunity.graduation.domain.accounts.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smunity.graduation.domain.accounts.annotation.UserResolver;
import com.smunity.graduation.domain.accounts.dto.UserRegisterRequestDto;
import com.smunity.graduation.domain.accounts.dto.UserRegisterResponseDto;
import com.smunity.graduation.domain.accounts.entity.User;
import com.smunity.graduation.domain.accounts.jwt.dto.JwtDto;
import com.smunity.graduation.domain.accounts.jwt.exception.AccountsExceptionHandler;
import com.smunity.graduation.domain.accounts.jwt.exception.TokenErrorCode;
import com.smunity.graduation.domain.accounts.jwt.util.JwtUtil;
import com.smunity.graduation.domain.accounts.service.AccountsService;
import com.smunity.graduation.global.common.ApiResponse;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/accounts")
@RestController
public class AccountsController {

	private final AccountsService accountsService;

	private final JwtUtil jwtUtil;

	@PostMapping("/register")
	public ApiResponse<UserRegisterResponseDto> register(@Valid @RequestBody UserRegisterRequestDto request) {
		return ApiResponse.onSuccess(accountsService.register(request));
	}

	@GetMapping("/reissue")
	public ApiResponse<JwtDto> reissueToken(@RequestHeader("RefreshToken") String refreshToken) {
		try {
			jwtUtil.validateRefreshToken(refreshToken);
			return ApiResponse.onSuccess(jwtUtil.reissueToken(refreshToken));
		} catch (ExpiredJwtException eje) {
			throw new AccountsExceptionHandler(TokenErrorCode.TOKEN_EXPIRED);
		} catch (IllegalArgumentException iae) {
			throw new AccountsExceptionHandler(TokenErrorCode.INVALID_TOKEN);
		}
	}

	@GetMapping("/test")
	public ApiResponse<String> testUserResolver(@UserResolver User user, HttpServletRequest request) {
		log.info(request.getHeader("Authorization"));
		return ApiResponse.onSuccess(user.getUserName());
	}
}
