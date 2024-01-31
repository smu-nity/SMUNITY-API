package com.smunity.graduation.domain.course.dto;

import com.smunity.graduation.domain.course.entity.Course;
import lombok.Builder;

@Builder
public record CourseResponseDto(
        Long id,
        String year,
        String semester,
        String number,
        String name,
        String type,
        String domain,
        int credit,
        boolean custom
) {

    public static CourseResponseDto from(Course course) {
        return CourseResponseDto.builder()
                .id(course.getId())
                .year(course.getYear())
                .semester(course.getSemester())
                .number(course.getSubject().getNumber())
                .name(course.getSubject().getName())
                .type(course.getType())
                .domain(course.getDomain())
                .credit(course.getCredit())
                .custom(course.isCustom())
                .build();
    }
}
