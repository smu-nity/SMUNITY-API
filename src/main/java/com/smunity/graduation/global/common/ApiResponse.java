package com.smunity.graduation.global.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.smunity.graduation.global.common.code.BaseCode;
import com.smunity.graduation.global.common.code.status.SuccessStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonPropertyOrder({"code", "message", "result"})
public class ApiResponse<T> {

	private final String code;
	private final String message;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private T result;

	// 성공한 경우 응답 생성

	public static <T> ApiResponse<T> onSuccess(T result) {
		return new ApiResponse<>(SuccessStatus._OK.getCode(), SuccessStatus._OK.getMessage(), result);
	}

	public static <T> ApiResponse<T> of(BaseCode code, T result) {
		return new ApiResponse<>(code.getReasonHttpStatus().getCode(), code.getReasonHttpStatus().getMessage(), result);
	}

	// 실패한 경우 응답 생성
	public static <T> ApiResponse<T> onFailure(String code, String message, T data) {
		return new ApiResponse<>(code, message, data);
	}
}

