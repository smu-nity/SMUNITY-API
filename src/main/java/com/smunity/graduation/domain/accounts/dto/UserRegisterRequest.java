package com.smunity.graduation.domain.accounts.dto;

import com.smunity.graduation.domain.accounts.entity.Department;
import com.smunity.graduation.domain.accounts.entity.Profile;
import com.smunity.graduation.domain.accounts.entity.User;
import com.smunity.graduation.domain.accounts.entity.Year;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserRegisterRequest(

	@NotEmpty(message = "[ERROR] 이름 입력은 필수 입니다.")
	String name,
	@NotEmpty(message = "[ERROR] 학번 입력은 필수 입니다.")
	String username,
	@NotEmpty(message = "[ERROR] 학과 입력은 필수 입니다.")
	String department,

	@NotEmpty(message = "[ERROR] 이메일 입력은 필수입니다.")
	@Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,6}$", message = "[ERROR] 이메일 형식에 맞지 않습니다.")
	String email,

	@NotEmpty(message = "[ERROR] 비밀번호 입력은 필수 입니다.")
	@Size(min = 8, message = "[ERROR] 비밀번호는 최소 8자리 이이어야 합니다.")
	@Pattern(regexp = "^(?=.*[a-z])(?=.*\\d)(?=.*[!@#$%^&*]).{8,64}$", message = "[ERROR] 비밀번호는 8자 이상, 64자 이하이며 특수문자 한 개를 포함해야 합니다.")
	String password,

	@NotEmpty(message = "[ERROR] 비밀번호 재확인 입력은 필수 입니다.")
	String passwordCheck
) {
	public User toEntity(String encodedPw) {
		return User.builder()
			.userName(username)
			.password(encodedPw)
			.email(email)
			.profile(null) // TODO 양방향 설정
			.build();

	}

	// Profile 만드는 부분은 record 분리해야??
	public Profile toProfile(User user, Year year, Department department) {
		return Profile.builder()
			.name(name)
			.user(user)
			.year(year)
			.department(department)
			.build();
	}
}
