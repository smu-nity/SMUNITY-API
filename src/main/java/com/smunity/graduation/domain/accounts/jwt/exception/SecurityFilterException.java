package com.smunity.graduation.domain.accounts.jwt.exception;

import com.smunity.graduation.global.common.code.BaseErrorCode;
import com.smunity.graduation.global.common.exception.GeneralException;

public class SecurityFilterException extends GeneralException {
	public SecurityFilterException(BaseErrorCode code) {
		super(code);
	}
}
