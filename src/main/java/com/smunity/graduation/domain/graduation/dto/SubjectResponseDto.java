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
        String type //이수구분

) {
    public static SubjectResponseDto from(Subject subject) {
        return SubjectResponseDto.builder()
                .number(subject.getNumber())
                .name(subject.getName())
                .semester(subject.getSemester())
                .grade(subject.getGrade())
                .credit(subject.getCredit())
                .type(subject.getType())
                .build();

    }
}
