package com.smunity.graduation.domain.accounts.jwt.exception;

import com.smunity.graduation.domain.accounts.jwt.exception.status.TokenErrorCode;

public class CustomNoTokenException extends RuntimeException {

	public CustomNoTokenException() {
		super(TokenErrorCode.NO_TOKEN.getCode());
	}
}
