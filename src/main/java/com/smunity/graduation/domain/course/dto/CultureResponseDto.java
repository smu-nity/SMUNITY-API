package com.smunity.graduation.domain.course.dto;

import com.smunity.graduation.domain.accounts.entity.User;
import com.smunity.graduation.domain.course.entity.Curriculum;
import com.smunity.graduation.global.common.type.SubDomain;
import lombok.Builder;

import java.util.List;

@Builder
public record CultureResponseDto(
        SubDomain subDomain,
        String subDomainName,
        boolean completed
) {

    private static CultureResponseDto of(SubDomain subDomain, User user) {
        return CultureResponseDto.builder()
                .subDomain(subDomain)
                .subDomainName(subDomain.getName())
                .completed(user.checkCompleted(subDomain))
                .build();
    }

    public static List<CultureResponseDto> of(List<Curriculum> curriculums, User user) {
        return curriculums.stream()
                .filter(curriculum -> !curriculum.getSubDomain().equals(user.getSubDomain()))
                .map(curriculum -> of(curriculum.getSubDomain(), user))
                .toList();
    }
}
