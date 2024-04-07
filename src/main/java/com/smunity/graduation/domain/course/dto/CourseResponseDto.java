package com.smunity.graduation.domain.course.dto;

import com.smunity.graduation.domain.course.entity.Course;
import com.smunity.graduation.global.common.type.Category;
import com.smunity.graduation.global.common.type.SubDomain;
import lombok.Builder;

import java.util.List;

@Builder
public record CourseResponseDto(
        Long id,
        String year,
        String semester,
        String number,
        String name,
        String type,
        String domain,
        Category category,
        SubDomain subDomain,
        int credit
) {

    public static CourseResponseDto from(Course course) {
        return CourseResponseDto.builder()
                .id(course.getId())
                .year(course.getYear())
                .semester(course.getSemester())
                .number(course.getNumber())
                .name(course.getName())
                .type(course.getType())
                .domain(course.getDomain())
                .category(course.getCategory())
                .subDomain(course.getSubDomain())
                .credit(course.getCredit())
                .build();
    }

    public static List<CourseResponseDto> from(List<Course> courses) {
        return courses.stream().map(CourseResponseDto::from).toList();
    }
}
