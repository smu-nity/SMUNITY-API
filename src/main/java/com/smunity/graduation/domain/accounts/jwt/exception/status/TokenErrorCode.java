package com.smunity.graduation.domain.accounts.jwt.exception.status;

import static org.springframework.http.HttpStatus.*;

import org.springframework.http.HttpStatus;

import com.smunity.graduation.global.common.code.BaseErrorCode;
import com.smunity.graduation.global.common.code.ErrorReasonDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TokenErrorCode implements BaseErrorCode {

	NO_TOKEN(BAD_REQUEST, "TOKEN4000", "토큰이 존재하지 않습니다."),
	TOKEN_EXPIRED(BAD_REQUEST, "TOKEN4001", "만료된 토큰입니다."),
	INVALID_FORM_TOKEN(BAD_REQUEST, "TOKEN4002", "유효하지 않은 형식의 토큰입니다."),
	INTERNAL_TOKEN_EXCEPTION(BAD_REQUEST, "TOKEN4003", "토큰 에러입니다."),
	SIGNATURE_ERROR(BAD_REQUEST, "TOKEN4004", "무결하지 않은 토큰입니다."),
	INTERNAL_SECURITY_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "5001", "서버 에러가 발생했습니다. 관리자에게 문의해주세요.");

	private final HttpStatus httpStatus;
	private final String code;
	private final String message;

	@Override
	public ErrorReasonDTO getReason() {
		return null;
	}

	@Override
	public ErrorReasonDTO getReasonHttpStatus() {
		return null;
	}
}
