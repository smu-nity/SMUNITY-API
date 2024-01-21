package com.smunity.graduation.domain.accounts.dto;

import com.smunity.graduation.domain.accounts.entity.User;

import lombok.Builder;

@Builder
public record UserRegisterResponse(
	Long id,
	String email,
	String userName,
	String name,
	String year,
	String department

) {

	public static UserRegisterResponse from(User user) {
		return UserRegisterResponse.builder()
			.email(user.getEmail())
			.userName(user.getUserName())
			.name(user.getName())
			.year(user.getYear().getYear())
			.department(user.getDepartment().getName())
			.build();
	}
}
