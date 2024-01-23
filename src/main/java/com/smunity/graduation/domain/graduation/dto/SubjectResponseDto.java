package com.smunity.graduation.domain.graduation.dto;

import ch.qos.logback.core.status.StatusBase;
import com.smunity.graduation.domain.graduation.entity.Subject;
import lombok.Builder;

@Builder
public record SubjectResponseDto(
        int grade, //학년
        int semester, //학기
        int subject_credit,
        String type, //이수구분
        String subject_number, //학수번호
        String subject_name //이름

) {
    public static SubjectResponseDto from(Subject subject,) {
        return SubjectResponseDto.builder()
                .grade(subject)
    }
}
