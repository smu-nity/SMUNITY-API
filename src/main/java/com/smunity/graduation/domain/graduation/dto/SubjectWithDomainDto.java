package com.smunity.graduation.domain.graduation.dto;

import com.smunity.graduation.domain.course.entity.Course;
import lombok.Builder;

@Builder
public record SubjectWithDomainDto(

        String name, //이름
        String number, //학수번호
        Integer credit, //학점
        String domain //구분

) {
    
    public static SubjectWithDomainDto to(Course course, String domain) {
        return SubjectWithDomainDto.builder()
                .name(course.getName())
                .number(course.getNumber())
                .credit(course.getCredit())
                .domain(domain)
                .build();
    }
}
