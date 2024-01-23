package com.smunity.graduation.domain.accounts.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smunity.graduation.domain.accounts.annotation.Accounts;
import com.smunity.graduation.domain.accounts.dto.UserRegisterRequestDto;
import com.smunity.graduation.domain.accounts.dto.UserRegisterResponseDto;
import com.smunity.graduation.domain.accounts.entity.User;
import com.smunity.graduation.domain.accounts.jwt.util.JwtUtil;
import com.smunity.graduation.domain.accounts.service.AccountsService;
import com.smunity.graduation.global.common.ApiResponse;

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

	@GetMapping("/test")
	public ApiResponse<String> register(@Accounts User user, HttpServletRequest request) {
		log.info(jwtUtil.getUsername(jwtUtil.resolveAccessToken(request)));
		log.info("===============");
		log.info(user.getUserName());
		log.info("===============");
		return ApiResponse.onSuccess(user.toString());
	}
}
