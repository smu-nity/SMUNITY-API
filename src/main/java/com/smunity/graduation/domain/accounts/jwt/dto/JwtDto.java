package com.smunity.graduation.domain.accounts.jwt.dto;

public record JwtDto(
	String accessToken,
	String refreshToken
) {
}
