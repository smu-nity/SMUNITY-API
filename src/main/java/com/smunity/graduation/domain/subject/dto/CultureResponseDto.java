package com.smunity.graduation.domain.subject.dto;

import com.smunity.graduation.domain.subject.entity.Culture;
import lombok.Builder;

import java.util.List;

@Builder
public record CultureResponseDto(
        Long id,
        String number,
        String name,
        String type,
        int credit
) {

    public static CultureResponseDto from(Culture culture) {
        return CultureResponseDto.builder()
                .id(culture.getId())
                .number(culture.getNumber())
                .name(culture.getName())
                .type(culture.getSubDomain().getName())
                .credit(culture.getCredit())
                .build();
    }

    public static List<CultureResponseDto> from(List<Culture> cultures) {
        return cultures.stream()
                .map(CultureResponseDto::from)
                .toList();
    }
}
