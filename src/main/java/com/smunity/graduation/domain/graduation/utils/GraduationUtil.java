package com.smunity.graduation.domain.graduation.utils;

import com.smunity.graduation.domain.graduation.dto.SubjectResponseDto;
import com.smunity.graduation.domain.graduation.entity.Subject;
import com.smunity.graduation.domain.graduation.repository.SubjectRepository;
import com.smunity.graduation.domain.graduation.temporary.CourseTemporary;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class GraduationUtil {

    private final SubjectRepository subjectRepository;

    /**
     * [ 교양 추천 과목 조회 - 일반 교양, 기초 교양, 상명핵역량교양, 균형교양 ]
     * Param type : 구분 (culture/culture_b/culture_e/culture_s)
     * Param credit : 필요 학점 (0일 수 있음)
     * Param domain : 영역명(일반/기초/핵심/균형) @Nullable
     * Param subdomain : 세부영역명( @Nullable
     * * * * * * * * * * * * * * 기초 - 사고와표현/EnglishforAcademicPurposes/기초수학/컴퓨팅사고와데이터의이해/알고리즘과게임콘텐츠
     * * * * * * * * * * * * * * 핵심 - 전문지식탐구역량/창의적문제해결역량/융복합역량/다양성존중역량/윤리실천역량
     * * * * * * * * * * * * * * 균형 - 인문/사회/자연/공학/예술
     * * * * * * * * * * * * * * )
     */
    public List<SubjectResponseDto> getRecommendCultureSubjects
    (String type, int credit, String domain, String subdomain, List<CourseTemporary> courses) {
        List<SubjectResponseDto> result = new ArrayList<>();
        switch (type) {
            case "culture":
                //일반 교양
                List<Subject> queryResults = subjectRepository.findAllCulturesWithCredit(credit);
                return excludeCompletedCourses(queryResults, courses).stream()
                        .map(SubjectResponseDto::from)
                        .collect(Collectors.toList());
            case "culture_b":
                //기초 교양
                List<Subject> queryResults = subjectRepository.findAllCulturesWithCredit(credit);
                return excludeCompletedCourses(queryResults, courses).stream()
                        .map(SubjectResponseDto::from)
                        .collect(Collectors.toList());

        }
        return result;
    }

    /**
     * [ 전공 추천 과목 조회 - 전공선택, 전공심화 ]
     * Param type : 구분 (major_i/major_s)
     * Param grade : 학년 (1학년/2학년/3학년/4학년)
     * Param semester : 학기 (1학기/2학기)  -> 필요한 학기를 전달
     * Param credit : 필요 학점 (0일 수 있음)
     * Param dept : 학과
     */
    public List<SubjectResponseDto> getRecommendMajorSubjects
    (String type, String grade, String semester, int credit, String dept) {
        List<SubjectResponseDto> result = new ArrayList<>();

        return result;
    }

    private List<Subject> excludeCompletedCourses(List<Subject> data, List<CourseTemporary> userData) {
        return data.stream()
                .filter(subject -> userData.stream()
                        .noneMatch(completedSubject -> completedSubject.getSubject().getId() == subject.getId()))
                .collect(Collectors.toList());
    }
}
