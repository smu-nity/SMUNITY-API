package com.smunity.graduation.accounts.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smunity.graduation.accounts.service.AccountsService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
@RestController
public class AccountsController {
	private final AccountsService accountsService;
}
