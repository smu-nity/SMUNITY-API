package com.smunity.graduation.domain.graduation.dto;

import com.smunity.graduation.domain.graduation.entity.Subject;
import lombok.Builder;

@Builder
public record SubjectWithDomainDto(

        String name, //이름
        String number, //학수번호
        Integer credit, //학점
        String domain //구분

) {
    public static SubjectWithDomainDto to(Subject subject, String domain) {
        return SubjectWithDomainDto.builder()
                .name(subject.getName())
                .number(subject.getNumber())
                .credit(subject.getCredit())
                .domain(domain)
                .build();
    }
}
