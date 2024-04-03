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
                .required(Math.max(0, total - completed))
                .completion(Math.min(100, completed * 100 / total))
                .build();
    }
}
