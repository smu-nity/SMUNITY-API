package com.smunity.graduation.domain.accounts.jwt.exception;

import com.smunity.graduation.domain.accounts.jwt.exception.status.TokenErrorCode;

public class CustomSignatureException extends RuntimeException {

	public CustomSignatureException() {
		super(TokenErrorCode.SIGNATURE_ERROR.getCode());
	}
}
