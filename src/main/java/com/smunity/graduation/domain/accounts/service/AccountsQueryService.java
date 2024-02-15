package com.smunity.graduation.domain.accounts.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smunity.graduation.domain.accounts.entity.User;
import com.smunity.graduation.domain.accounts.exception.AccountsExceptionHandler;
import com.smunity.graduation.domain.accounts.repository.user.UserRepository;
import com.smunity.graduation.global.common.ErrorCode;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class AccountsQueryService {

	private final UserRepository userRepository;

	public User findByUserName(String username) {
		return userRepository.findByUserName(username)
			.orElseThrow(() -> new AccountsExceptionHandler(ErrorCode.USER_NOT_FOUND));
	}
}
