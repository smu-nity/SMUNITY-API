package com.smunity.graduation.domain.course.dto;

import com.smunity.graduation.global.common.enums.SubDomain;
import lombok.Builder;

import java.util.List;

@Builder
public record CultureResponseDto(
        SubDomain subDomain,
        String subDomainName,
        boolean completed
) {

    public static CultureResponseDto of(SubDomain curriculum, List<SubDomain> courses) {
        return CultureResponseDto.builder()
                .subDomain(curriculum)
                .subDomainName(curriculum.getName())
                .completed(courses.contains(curriculum))
                .build();
    }

    public static List<CultureResponseDto> of(List<SubDomain> curriculums, List<SubDomain> courses) {
        return curriculums.stream()
                .map(curriculum -> of(curriculum, courses))
                .toList();
    }
}
