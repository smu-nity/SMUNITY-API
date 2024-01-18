package com.smunity.graduation.domain.accounts.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smunity.graduation.domain.accounts.dto.UserRegisterRequest;
import com.smunity.graduation.domain.accounts.dto.UserRegisterResponse;
import com.smunity.graduation.domain.accounts.service.AccountsService;
import com.smunity.graduation.global.common.ApiResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
@RestController
public class AccountsController {

	private final AccountsService accountsService;

	@PostMapping("/register")
	public ApiResponse<UserRegisterResponse> register(@Valid @RequestBody UserRegisterRequest request) {
		return ApiResponse.onSuccess(accountsService.register(request));
	}
}
