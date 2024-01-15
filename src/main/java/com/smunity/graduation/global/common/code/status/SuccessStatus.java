package com.smunity.graduation.global.common.code.status;

import org.springframework.http.HttpStatus;

import com.smunity.graduation.global.common.code.BaseCode;
import com.smunity.graduation.global.common.code.ReasonDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SuccessStatus implements BaseCode {

	// 일반적인 응답
	_OK(HttpStatus.OK, "COMMON200", "성공입니다.");

	// 멤버 관련 응답

	// ~~~ 관련 응답

	private final HttpStatus httpStatus;
	private final String code;
	private final String message;

	@Override
	public ReasonDTO getReason() {
		return ReasonDTO.builder()
			.message(message)
			.code(code)
			// .isSuccess(true)
			.build();
	}

	@Override
	public ReasonDTO getReasonHttpStatus() {
		return ReasonDTO.builder()
			.message(message)
			.code(code)
			// .isSuccess(true)
			.httpStatus(httpStatus)
			.build()
			;
	}
}
