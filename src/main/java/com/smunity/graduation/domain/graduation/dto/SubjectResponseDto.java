package com.smunity.graduation.domain.graduation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@AllArgsConstructor
@SuperBuilder
public class SubjectResponseDto {
    String name;

    //총 기준 학점
    int total;

    //총 이수 학점
    int count;

    //전공
    int major;

    //교양
    int culture;

    //일반
    int common;

    //필요 학점
    int lack;

    List<SubjectWithDomainDto> subjects;

    public static SubjectResponseDto to(String name, int total, int count) {
        return SubjectResponseDto.builder()
                .name(name)
                .total(total)
                .count(count)
                .lack(Math.max(total - count, 0))
                .build();
    }

    public static SubjectResponseDto to(String name, int total, int count, List<SubjectWithDomainDto> subjects) {
        return SubjectResponseDto.builder()
                .name(name)
                .total(total)
                .count(count)
                .lack(Math.max(total - count, 0))
                .subjects(subjects)
                .build();
    }

}

