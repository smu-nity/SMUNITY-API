package com.smunity.graduation.domain.accounts.jwt.exception;

import com.smunity.graduation.global.exception.code.BaseErrorCode;
import com.smunity.graduation.global.exception.CustomException;

import lombok.Getter;

@Getter
public class SecurityCustomException extends CustomException {

	private final Throwable cause;

	public SecurityCustomException(BaseErrorCode errorCode) {
		super(errorCode);
		this.cause = null;
	}

	public SecurityCustomException(BaseErrorCode errorCode, Throwable cause) {
		super(errorCode);
		this.cause = cause;
	}
}
