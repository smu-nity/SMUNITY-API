package com.smunity.graduation.domain.graduation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@AllArgsConstructor
@SuperBuilder
public class SubjectResultResponseDto {
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

    public static SubjectResultResponseDto to(String name, int total, int count) {
        return SubjectResultResponseDto.builder()
                .name(name)
                .total(total)
                .count(count)
                .lack(Math.max(total - count, 0))
                .build();
    }

    public static SubjectResultResponseDto to(String name, int total, int count, List<SubjectWithDomainDto> subjects) {
        return SubjectResultResponseDto.builder()
                .name(name)
                .total(total)
                .count(count)
                .lack(Math.max(total - count, 0))
                .subjects(subjects)
                .build();
    }

    @Override
    public String toString() {
        return "이름 : " + name + "\n" +
                "기준 학점 : " + total + "\n" +
                "이수 학점 : " + count + "\n" +
                "전공 : " + major + "\n" +
                "교양 : " + culture + "\n" +
                "일반 : " + common + "\n" +
                "부족 : " + lack + "\n"
                ;
    }

}

