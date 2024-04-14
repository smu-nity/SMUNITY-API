package com.smunity.graduation.domain.course.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record ResultResponseDto<T>(
        boolean completed,
        StatusResponseDto status,
        int count,
        List<T> content
) {

    public static <T> ResultResponseDto<T> of(int total, int completed, List<T> responses) {
        return ResultResponseDto.<T>builder()
                .completed(total <= completed)
                .status(StatusResponseDto.of(total, completed))
                .count(responses.size())
                .content(responses)
                .build();
    }
}
