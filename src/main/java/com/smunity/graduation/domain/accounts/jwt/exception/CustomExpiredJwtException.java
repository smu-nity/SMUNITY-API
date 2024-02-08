package com.smunity.graduation.domain.accounts.jwt.exception;

import com.smunity.graduation.domain.accounts.jwt.exception.status.TokenErrorCode;

public class CustomExpiredJwtException extends RuntimeException {

	public CustomExpiredJwtException() {
		super(TokenErrorCode.TOKEN_EXPIRED.getCode());
	}
}
