package com.smunity.graduation.global.common.exception;

import com.smunity.graduation.global.common.BaseErrorCode;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {

	// TODO transient 테스트
	private final transient BaseErrorCode errorCode;

	public CustomException(BaseErrorCode errorCode) {
		this.errorCode = errorCode;
	}
}
