package com.smunity.graduation.domain.graduation.dto;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class TotalSubjectResponseDto extends SubjectResponseDto {

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

}

