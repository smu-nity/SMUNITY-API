package com.smunity.graduation.domain.graduation.dto;

import com.smunity.graduation.domain.graduation.entity.Subject;
import lombok.Builder;

@Builder
public record SubjectResponseDto(
        String number, //학수번호
        String name, //이름
        int semester, //학기
        int grade, //학년
        int credit, //학점
        String type //이수구분

) {
    public static SubjectResponseDto from(Subject subject) {
        return null;
//                SubjectResponseDto.builder()
//                .grade(subject)
    }
}
