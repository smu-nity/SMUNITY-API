package com.smunity.graduation.global.common;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode implements BaseErrorCode {

	// 가장 일반적인 응답
	_INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON500", "서버 에러, 관리자에게 문의 바랍니다."),
	_BAD_REQUEST(HttpStatus.BAD_REQUEST, "COMMON400", "잘못된 요청입니다."),
	_UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "COMMON401", "인증이 필요합니다."),
	_FORBIDDEN(HttpStatus.FORBIDDEN, "COMMON403", "금지된 요청입니다."),

	// 멤버 관련 에러
	USER_NOT_FOUND(HttpStatus.BAD_REQUEST, "USER4001", "사용자가 없습니다."),
	NICKNAME_NOT_EXIST(HttpStatus.BAD_REQUEST, "USER4002", "닉네임은 필수 입니다."),
	PASSWORD_NOT_EQUAL(HttpStatus.BAD_REQUEST, "USER4003", "비밀번호가 일치하지 않습니다."),
	USER_ALREADY_EXIST(HttpStatus.BAD_REQUEST, "USER4004", "사용자가 이미 존재합니다."),
	SAMNUL_ERROR(HttpStatus.BAD_REQUEST, "SAM4001", "샘물 에러입니다."),

	// auth 관련 에러
	AUTH_UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "AUTH401", "아이디 및 비밀번호가 일치하지 않습니다."),
	AUTH_INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "AUTH500", "인증 서버 에러, 관리자에게 문의 바랍니다."),

	// graduation 관련 에러
	SUBJECT_NOT_FOUND(HttpStatus.NOT_FOUND, "SUBJECT404", "해당 과목이 없습니다."),

	// qna 관련 에러
	QUESTION_NOT_FOUND(HttpStatus.NOT_FOUND, "QUESTION4001", "해당 질문이 없습니다."),
	AUTHOR_NOT_MATCHED(HttpStatus.FORBIDDEN, "QUESTION4002", "작성자가 일치하지 않습니다."),
	ANSWER_NOT_FOUND(HttpStatus.NOT_FOUND, "ANSWER4001", "해당 답변이 없습니다."),
	AUTHOR_IS_NOT_STAFF(HttpStatus.FORBIDDEN, "ANSWER4002", "스태프가 아닙니다."),

	// 예시,,,
	ARTICLE_NOT_FOUND(HttpStatus.NOT_FOUND, "ARTICLE4001", "게시글이 없습니다."),

	// For test
	TEMP_EXCEPTION(HttpStatus.BAD_REQUEST, "TEMP4001", "이거는 테스트");

	private final HttpStatus httpStatus;
	private final String code;
	private final String message;

	@Override
	public ApiResponse<Void> getErrorResponse() {
		return ApiResponse.onFailure(code, message);
	}
}
