package com.smunity.graduation.domain.accounts.dto;

import com.smunity.graduation.domain.accounts.entity.User;

import lombok.Builder;

@Builder
public record UserRegisterResponse(
	Long id,
	String email,
	String userName
) {

	public static UserRegisterResponse from(User user) {
		return UserRegisterResponse.builder()
			.id(user.getId())
			.email(user.getEmail())
			.userName(user.getUserName())
			.build();
	}
}
