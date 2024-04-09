package com.smunity.graduation.domain.subject.dto;

import com.smunity.graduation.domain.subject.entity.Major;
import lombok.Builder;

import java.util.List;

@Builder
public record ResultResponseDto(
        int count,
        List<?> content
) {

    public static ResultResponseDto from(List<Major> majors) {
        return ResultResponseDto.builder()
                .count(majors.size())
                .content(MajorResponseDto.from(majors))
                .build();
    }
}
