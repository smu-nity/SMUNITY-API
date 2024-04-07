package com.smunity.graduation.domain.subject.dto;

import com.smunity.graduation.domain.subject.entity.Major;
import lombok.Builder;

import java.util.List;

@Builder
public record MajorResponseDto(
        Long id,
        String grade,
        String semester,
        String number,
        String name,
        String type,
        int credit
) {

    public static MajorResponseDto from(Major major) {
        return MajorResponseDto.builder()
                .id(major.getId())
                .grade(major.getGrade().getName())
                .semester(major.getSemester().getName())
                .number(major.getNumber())
                .name(major.getName())
                .type(major.getType())
                .build();
    }

    public static List<MajorResponseDto> from(List<Major> majors) {
        return majors.stream()
                .map(MajorResponseDto::from)
                .toList();
    }
}
