package com.smunity.graduation.global.common.code;

import org.springframework.http.HttpStatus;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReasonDTO {
	
	private final String code;
	private final String message;
	private HttpStatus httpStatus;
}
