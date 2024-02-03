package com.smunity.graduation.domain.accounts.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smunity.graduation.domain.accounts.annotation.UserResolver;
import com.smunity.graduation.domain.accounts.dto.UserRegisterRequestDto;
import com.smunity.graduation.domain.accounts.dto.UserRegisterResponseDto;
import com.smunity.graduation.domain.accounts.entity.User;
import com.smunity.graduation.domain.accounts.service.AccountsService;
import com.smunity.graduation.global.common.ApiResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/accounts")
@RestController
public class AccountsController {

	private final AccountsService accountsService;

	@PostMapping("/register")
	public ApiResponse<UserRegisterResponseDto> register(@Valid @RequestBody UserRegisterRequestDto request) {
		return ApiResponse.onSuccess(accountsService.register(request));
	}

	@GetMapping("/test")
	public ApiResponse<String> register(@UserResolver User user) {
		return ApiResponse.onSuccess(user.getUserName());
	}
}
