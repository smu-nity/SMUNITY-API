package com.smunity.graduation.domain.accounts.dto;

import com.smunity.graduation.domain.accounts.entity.User;

import lombok.Builder;

@Builder
public record UserRegisterResponseDto(
	Long id,
	String email,
	String userName,
	String name,
	String year,
	String department,
	int currentYear,
	int completedSemesters

) {

	public static UserRegisterResponseDto from(User user) {
		return UserRegisterResponseDto.builder()
			.email(user.getEmail())
			.userName(user.getUserName())
			.name(user.getName())
			.year(user.getYear().getYear())
			.department(user.getDepartment().getName())
			.currentYear(user.getCurrentYear())
			.completedSemesters(user.getCompletedSemesters())
			.build();
	}
}
