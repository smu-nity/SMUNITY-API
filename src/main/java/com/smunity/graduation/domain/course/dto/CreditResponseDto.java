package com.smunity.graduation.domain.course.dto;

import lombok.Builder;

import static com.smunity.graduation.domain.course.dto.StatusResponseDto.calculateCompletion;
import static com.smunity.graduation.domain.course.dto.StatusResponseDto.calculateRequired;

@Builder
public record CreditResponseDto(
        int total,
        int completed,
        int major,
        int culture,
        int etc,
        int required,
        int completion
) {

    public static CreditResponseDto of(int total, int completed, int major, int culture) {
        return CreditResponseDto.builder()
                .total(total)
                .completed(completed)
                .major(major)
                .culture(culture)
                .etc(completed - major - culture)
                .required(calculateRequired(total, completed))
                .completion(calculateCompletion(total, completed))
                .build();
    }
}
