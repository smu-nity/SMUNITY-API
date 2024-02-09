package com.smunity.graduation.domain.auth.dto;

import com.smunity.graduation.domain.course.entity.Course;

public record AuthCourseResponseDto(
        String number,
        String name,
        String type,
        String grade,
        String year,
        String semester,
        String domain,
        int credit
) {

    public Course toEntity() {
        return Course.builder()
                .name(name)
                .number(number)
                .year(year)
                .semester(semester)
                .type(type)
                .domain(domain)
                .credit(credit)
                .build();
    }
}
