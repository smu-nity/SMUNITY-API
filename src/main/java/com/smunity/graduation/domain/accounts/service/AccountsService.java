package com.smunity.graduation.domain.accounts.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smunity.graduation.domain.accounts.dto.UserRegisterRequestDto;
import com.smunity.graduation.domain.accounts.dto.UserRegisterResponseDto;
import com.smunity.graduation.domain.accounts.entity.Department;
import com.smunity.graduation.domain.accounts.entity.User;
import com.smunity.graduation.domain.accounts.entity.Year;
import com.smunity.graduation.domain.accounts.jwt.exception.AccountsExceptionHandler;
import com.smunity.graduation.domain.accounts.repository.DepartmentJpaRepository;
import com.smunity.graduation.domain.accounts.repository.YearJpaRepository;
import com.smunity.graduation.domain.accounts.repository.user.UserJpaRepository;
import com.smunity.graduation.global.common.ErrorCode;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class AccountsService {

	private final UserJpaRepository userJpaRepository;
	private final YearJpaRepository yearJpaRepository;
	private final DepartmentJpaRepository departmentJpaRepository;
	private final PasswordEncoder passwordEncoder;

	public UserRegisterResponseDto register(UserRegisterRequestDto request) {

		if (!request.password().equals(request.passwordCheck()))
			throw new AccountsExceptionHandler(ErrorCode.PASSWORD_NOT_EQUAL);

		String encodedPw = passwordEncoder.encode(request.password());
		User newUser = request.toEntity(encodedPw);

		Year year = yearJpaRepository.findByYear(request.username().substring(0, 4))
			.orElseThrow(() -> new AccountsExceptionHandler(
				ErrorCode.SAMNUL_ERROR));
		Department department = departmentJpaRepository.findByName(request.department())
			.orElseThrow(() -> new AccountsExceptionHandler(ErrorCode.SAMNUL_ERROR));

		newUser.setYear(year);
		newUser.setDepartment(department);

		return UserRegisterResponseDto.from(userJpaRepository.save(newUser));
	}
}
