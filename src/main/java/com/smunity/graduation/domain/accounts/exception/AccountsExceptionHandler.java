package com.smunity.graduation.domain.accounts.exception;

import com.smunity.graduation.global.common.code.BaseErrorCode;
import com.smunity.graduation.global.common.exception.GeneralException;

public class AccountsExceptionHandler extends GeneralException {
	public AccountsExceptionHandler(BaseErrorCode code) {
		super(code);
	}
}
