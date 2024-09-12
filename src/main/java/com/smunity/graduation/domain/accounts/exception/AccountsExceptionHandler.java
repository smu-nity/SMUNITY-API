package com.smunity.graduation.domain.accounts.exception;

import com.smunity.graduation.global.exception.code.BaseErrorCode;
import com.smunity.graduation.global.exception.CustomException;

public class AccountsExceptionHandler extends CustomException {
	public AccountsExceptionHandler(BaseErrorCode code) {
		super(code);
	}
}
