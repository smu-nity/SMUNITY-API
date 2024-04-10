package com.smunity.graduation.domain.subject.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record ResultResponseDto<T>(
        int count,
        List<T> content
) {

    public static <T> ResultResponseDto<T> from(List<T> responses) {
        return ResultResponseDto.<T>builder()
                .count(responses.size())
                .content(responses)
                .build();
    }
}
