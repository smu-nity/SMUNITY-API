package com.smunity.graduation.domain.course.dto;

import com.smunity.graduation.domain.course.entity.Course;
import com.smunity.graduation.global.common.type.SubDomain;
import lombok.Builder;

import java.util.List;

@Builder
public record ResultResponseDto(
        boolean completed,
        StatusResponseDto status,
        int count,
        List<?> content
) {

    public static ResultResponseDto of(int total, List<Course> courses) {
        int completed = calculateCompleted(courses);
        return ResultResponseDto.builder()
                .completed(total <= completed)
                .status(StatusResponseDto.of(total, completed))
                .count(courses.size())
                .content(CourseResponseDto.from(courses))
                .build();
    }

    public static ResultResponseDto of(int total, List<SubDomain> curriculums, List<SubDomain> courses) {
        List<CultureResponseDto> cultures = CultureResponseDto.of(curriculums, courses);
        int completed = calculateCultureCompleted(cultures);
        return ResultResponseDto.builder()
                .completed(total <= completed)
                .status(StatusResponseDto.of(total, completed))
                .count(total)
                .content(CultureResponseDto.of(curriculums, courses))
                .build();
    }

    private static int calculateCompleted(List<Course> courses) {
        return courses.stream()
                .mapToInt(Course::getCredit)
                .sum();
    }

    private static int calculateCultureCompleted(List<CultureResponseDto> cultures) {
        return cultures.stream()
                .filter(CultureResponseDto::completed)
                .toList()
                .size();
    }
}
