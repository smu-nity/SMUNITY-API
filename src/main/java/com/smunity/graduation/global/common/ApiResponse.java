package com.smunity.graduation.global.common;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

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
		return new ApiResponse<>(HttpStatus.OK.name(), HttpStatus.OK.getReasonPhrase(), result);
	}

	// 실패한 경우 응답 생성
	public static <T> ApiResponse<T> onFailure(String code, String message, T data) {
		return new ApiResponse<>(code, message, data);
	}

	public static <T> ApiResponse<T> onFailure(String statusCode, String message) {
		return new ApiResponse<>(statusCode, message, null);
	}

	// 삭제된 경우 응답 생성
	public static <T> ApiResponse<T> noContent() {
		return new ApiResponse<>(HttpStatus.NO_CONTENT.getReasonPhrase(), "", null);
	}

	// Json Serialize
	public String toJsonString() throws JsonProcessingException {
		return new ObjectMapper().writeValueAsString(this);
	}
}

