package com.smunity.graduation.domain.accounts.jwt.exception;

import com.smunity.graduation.global.common.BaseErrorCode;
import com.smunity.graduation.global.common.exception.CustomException;

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
