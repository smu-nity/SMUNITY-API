package com.smunity.graduation.domain.course.dto;

import lombok.Builder;

@Builder
public record StatusResponseDto(
        int total,
        int completed,
        int required,
        int completion
) {

    public static StatusResponseDto of(int total, int completed) {
        return StatusResponseDto.builder()
                .total(total)
                .completed(completed)
                .required(calculateRequired(total, completed))
                .completion(calculateCompletion(total, completed))
                .build();
    }

    public static int calculateRequired(int total, int completed) {
        return Math.max(0, total - completed);
    }

    public static int calculateCompletion(int total, int completed) {
        return Math.min(100, completed * 100 / total);
    }
}
