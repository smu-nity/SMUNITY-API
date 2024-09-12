package com.smunity.graduation.global.exception;

import com.smunity.graduation.global.exception.code.BaseErrorCode;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {

	private final BaseErrorCode errorCode;

	public CustomException(BaseErrorCode errorCode) {
		this.errorCode = errorCode;
	}
}
