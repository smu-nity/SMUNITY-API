package com.smunity.graduation.domain.graduation.dto;

import lombok.Builder;

import java.util.List;

public record GraduationResponseDto(

    List<SubjectResponseDto> subjects

) {
    @Builder
    public record SubjectResponseDto(
            String name,
            int total,
            int count,
            int major,
            int culture,
            int common,
            int lack
    ) {
    }

}

