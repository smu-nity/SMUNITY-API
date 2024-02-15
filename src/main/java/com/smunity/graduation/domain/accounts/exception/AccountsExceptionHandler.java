package com.smunity.graduation.domain.accounts.exception;

import com.smunity.graduation.global.common.BaseErrorCode;
import com.smunity.graduation.global.common.exception.CustomException;

public class AccountsExceptionHandler extends CustomException {
	public AccountsExceptionHandler(BaseErrorCode code) {
		super(code);
	}
}
