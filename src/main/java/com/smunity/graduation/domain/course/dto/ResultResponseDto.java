package com.smunity.graduation.domain.course.dto;

import com.smunity.graduation.domain.course.entity.Course;
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
                .completed(total < completed)
                .status(StatusResponseDto.of(total, completed))
                .count(courses.size())
                .content(CourseResponseDto.from(courses))
                .build();
    }

    private static int calculateCompleted(List<Course> courses) {
        return courses.stream().mapToInt(Course::getCredit).sum();
    }
}
