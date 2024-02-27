package com.smunity.graduation.domain.graduation.dto;

import com.smunity.graduation.domain.graduation.entity.Subject;

import lombok.Builder;

@Builder
public record SubjectResponseDto(
	String number, //학수번호
	String name, //이름
	String semester, //학기
	String grade, //학년
	int credit, //학점
	String type, //이수구분
	Integer count

) {
	public static SubjectResponseDto from(Subject subject) {
		return SubjectResponseDto.builder()
			.number(subject.getNumber())
			.name(subject.getName())
			.semester(subject.getSemester())
			.grade(subject.getGrade())
			.credit(subject.getCredit())
			.type(subject.getType())
			.count(subject.getCount())
			.build();

	}

	@Override
	public String toString() {
		return "학수번호 : " + number + "\n" +
			"이름 : " + name + "\n" +
			"힉기 : " + semester + "\n" +
			"학년 : " + grade + "\n" +
			"학점 : " + credit + "\n" +
			"이수구분 : " + type + "\n" +
			"수강 횟수 : " + count + "\n";
	}
}
