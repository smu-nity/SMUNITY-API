package com.smunity.graduation.domain.graduation.dto;

import com.smunity.graduation.domain.accounts.entity.User;
import com.smunity.graduation.domain.accounts.entity.Year;
import com.smunity.graduation.domain.graduation.entity.type.SubjectType;
import com.smunity.graduation.domain.graduation.temporary.CourseTemporary;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Builder
public record GraduationResponseDto(

        List<SubjectResponseDto> subjects

) {
    /**
     * [ 2018~2019학번 신입생 적용 교양교육과정 이수원칙 ]
     * - 기초 교양 (3개) CULTURE_B
     * 사고와 표현
     * EnglishforAcademicPurposes, 기초수학 중 택 1
     * 컴퓨팅사고와데이터의이해, 알고리즘과게임콘텐츠 중 택 1
     * <p>
     * - 상명핵심역량교양 CULTURE_E (2개)
     * 전문지식탐구역량, 창의적문제해결역량, 융복합역량, 다양성존중역량, 윤리실천역량 중 택 2
     * <p>
     * - 균형교양 CULTURE_B (3걔)
     * 인문, 사회, 자연, 공학, 예술 중 자신의 영역을 제외한 4개 중 택 3
     * <p>
     * - 일반 교양
     * 영역 구분 없이 자유럽게 선택 이수, 33학점 이상 이수
     * <p>
     * <p>
     * <p>
     * <p>
     * [ 2020~2021학번 신입생 적용 교양교육과정 이수원칙 ]
     * - 기초 교양 (4개) CULTURE_B
     * 사고와 표현
     * EnglishforAcademicPurposes, 기초수학 중 택 1
     * 컴퓨팅사고와데이터의이해
     * 알고리즘과게임콘텐츠
     * <p>
     * - 상명핵심역량교양 CULTURE_E (2개)
     * 전문지식탐구역량, 창의적문제해결역량, 융복합역량, 다양성존중역량, 윤리실천역량 중 택 2
     * <p>
     * - 균형교양 CULTURE_B (3걔)
     * 인문, 사회, 자연, 공학, 예술 중 자신의 영역을 제외한 4개 중 택 3
     * <p>
     * - 일반 교양
     * 영역 구분 없이 자유럽게 선택 이수, 33학점 이상 이수
     */
    public static GraduationResponseDto to(List<CourseTemporary> courses, Year year, User user) {

        List<SubjectResponseDto> subjects = new ArrayList<>();

        //TODO : 예외처리
        //TOTAL (전체)
        int total = year.getAll();
        int count = getAllCredits(courses);
        subjects.add(TotalSubjectResponseDto.builder()
                .name("total")
                //총 기준 학점
                .total(total)
                //총 이수 학점
                .count(count)
                //전공 = 전공 심화(MAJOR_I) + 전공 선택(MAJOR_S)
                .major(getCreditsBySubjectType(courses, List.of(SubjectType.MAJOR_I.getType(), SubjectType.MAJOR_S.getType())))
                //교양 = 교양 필수(CULTURE_E) + 교양 선택(CULTURE_S)
                .culture(getCreditsBySubjectType(courses, List.of(SubjectType.CULTURE_E.getType(), SubjectType.CULTURE_S.getType())))
                //일반 = 이수 과목 - 전공 심화(MAJOR_I) - 전공 선택(MAJOR_S) -  교양 필수(CULTURE_E) - 교양 선택(CULTURE_S)
                .common(getCreditsWithOutSubjectType(courses,
                        List.of(SubjectType.MAJOR_I, SubjectType.CULTURE_S,
                                SubjectType.CULTURE_E, SubjectType.CULTURE_S)))
                .lack(Math.max(total - count, 0))
                .build());

        //MAJOR_I (전공 심화)
        subjects.add(SubjectResponseDto.to(
                "major_i",
                year.getMajorI(),
                getCreditsBySubjectType(courses, List.of(SubjectType.MAJOR_I.getType())))
        );

        //MAJOR_S (전공 선택)
        subjects.add(SubjectResponseDto.to(
                "major_s",
                year.getMajorS(),
                getCreditsBySubjectType(courses, List.of(SubjectType.MAJOR_S.getType())))
        );

        //CULTURE (교양) = 교양 필수(CULTURE_E) + 교양 선택(CULTURE_S)
        subjects.add(SubjectResponseDto.to(
                "culture",
                year.getCulture(),
                getCreditsBySubjectType(courses, List.of(SubjectType.CULTURE_E.getType(), SubjectType.CULTURE_S.getType())))
        );

        //CULTURE_B (기초 교양)
        subjects.add(SubjectResponseDto.to(
                "culture_b",
                year.getCultureCnt(), //TODO : 18,19와 20,21학번 기초교양 기준 확인 -> 모두 4개 로 되어있음
                getBasicCultureCredits(courses, user))
        );

        //CULTURE_E (상명핵심역량교양)
        subjects.add(SubjectResponseDto.to(
                "culture_e",
                2,
                getSangmyungEssentialCultureCredits(courses))
        );

        //CULTURE_S (균형 교양) = 5개 영역 중 자신 영역 제외 4개 중 3개
        subjects.add(SubjectResponseDto.to(
                        "culture_s",
                        3,
                        getBalanceCultureCredits(courses, user)
                )
        );

        return GraduationResponseDto.builder()
                .subjects(subjects)
                .build();
    }

    private static int getCreditsBySubjectType(List<CourseTemporary> courses, List<String> subjectType) {
        return courses.stream()
                .filter(course -> subjectType.contains(course.getType()))
                .mapToInt(CourseTemporary::getCredit)
                .sum();
    }

    private static int getCreditsWithOutSubjectType(List<CourseTemporary> courses, List<SubjectType> subjectType) {
        return courses.stream()
                .filter(course -> !subjectType.contains(course.getType()))
                .mapToInt(CourseTemporary::getCredit)
                .sum();
    }

    //기초 교양(CULTURE_B) 계산
    private static int getBasicCultureCredits(List<CourseTemporary> courses, User user) {

        //TODO : 장애학생은 EnglishforAcademicPurposes」, 「기초수학」,「컴퓨팅사고와데이터의이해」 및「알고리즘과게임콘텐츠」이수 의무 없음
        //TODO : 외국인 유학생은 「컴퓨팅사고와데이터의이해」, 「알고리즘과게임콘텐츠」이수 의무 없음
        int result = 0;

        //기초 포함 domain 추출
        List<CourseTemporary> courseWithBasic = courses.stream()
                .filter(course -> course.getDomain() != null &&
                        (course.getDomain().contains("기초") ||
                                course.getDomain().contains("컴퓨팅")
                        ))
                .toList();
        log.info("[ 기초 교양 계산 ] domain 기초 포함 과목 수 : {}", courseWithBasic.size());

        //사고와 표현
        long val1 = courseWithBasic.stream()
                .filter(course -> course.getDomain().contains("사고와표현"))
                .limit(1)
                .count();
        log.info("[ 기초 교양 계산 ] 사고와 표현 value : {}", val1);
        result += val1;

        //EnglishFoundation or 기초수학
        long val2 = courseWithBasic.stream()
                .filter(course -> course.getDomain().contains("EnglishFoundation") || // 기초교양 1. EnglishforAcademicPurposes
                        course.getDomain().contains("영어1") || // EnglishFoundation
                        course.getDomain().contains("영어2") || // EnglishFoundation
                        course.getDomain().contains("기초수학") ||
                        course.getDomain().contains("기초미적분학") // 기초교양 2. 기초수학 개편 전
                )
                .limit(1)
                .count();
        log.info("[ 기초 교양 계산 ] EnglishforAcademicPurposes or 기초수학 value : {}", val2);
        result += val2;

        //컴퓨팅사고 or 알고리즘과게임 : 18, 19학번의 경우 택1, 20학번 부터는 각각 따로 이수 필요
        long val3 = 0;
        if (user.getUserName().startsWith("2019") || user.getUserName().startsWith("2018")) {
            //18, 19학번 : 컴퓨팅사고와데이터의이해와 알고리즘과게임콘텐츠 택 1
            val3 += courseWithBasic.stream()
                    .filter(course -> course.getDomain().contains("컴퓨팅사고와데이터의이해") ||
                            course.getDomain().contains("컴퓨팅사고와게임디자인") || // 컴퓨팅사고와데이터의이해 개편 전
                            course.getDomain().contains("컴퓨팅사고와문제해결") || // 컴퓨팅사고와데이터의이해 개편 전
                            course.getDomain().contains("알고리즘과게임콘텐츠"))
                    .limit(1)
                    .count();
            log.info("[ 기초 교양 계산 ] 19학번 - 컴퓨팅 or 알고리즘 value : {}", val3);
        } else {
            //그 외 20학번 이상 : 컴퓨팅사고와데이터의이해와 알고리즘과게임콘텐츠 각각 이수
            val3 += courseWithBasic.stream()
                    .filter(course -> course.getDomain().contains("컴퓨팅사고와데이터의이해") ||
                            course.getDomain().contains("컴퓨팅사고와게임디자인") || // 컴퓨팅사고와데이터의이해 개편 전
                            course.getDomain().contains("컴퓨팅사고와문제해결") // 컴퓨팅사고와데이터의이해 개편 전
                    )
                    .limit(1)
                    .count();

            val3 += courseWithBasic.stream()
                    .filter(course -> course.getDomain().contains("알고리즘과게임콘텐츠"))
                    .limit(1)
                    .count();
            log.info("[ 기초 교양 계산 ] 20학번 이상 - 컴퓨팅 value : {}", val3);
        }

        result += val3;

        //교양과 인성 -> 2023학년도 전기(2024.2.) 졸업예정자 졸업기준 2018~2021학번 교양과 인성 이수 필요 없어짐
//        long val4 = courseWithBasic.stream()
//                .filter(course -> course.getDomain().contains("교양과인성"))
//                .limit(1)
//                .count();
//        log.info("[ 기초 교양 계산 ] 교양과 인성 value : {}", val4);

        //사범대학 재학생은 교양과 인성 대신 「미래교사와인성」(구 「교직윤리와인성」) 교과목으로 대체 인정
//        if (user.getProfile().getDepartment().getCollege().equals("사범대학")) {
//            val4 = courses.stream()
//                    .filter(course -> course.getDomain().contains("미래교사와인성") ||
//                            course.getDomain().contains("교직윤리와인성"))
//                    .limit(1)
//                    .count();
//        }

        return result;
    }

    //상명핵심역량교양(CULTURE_E) 계산
    private static int getSangmyungEssentialCultureCredits(List<CourseTemporary> courses) {

        //TODO : 「상명CareerStart」 2020학년도부터 폐지됨, 미수강 및 재수강의 경우 소급 적용하여 이수 의무 없음

        //핵심 포함 domain 추출
        List<CourseTemporary> courseWithEssential = courses.stream()
                .filter(course -> course.getDomain() != null &&
                        course.getDomain().contains("핵심"))
                .toList();
        log.info("[ 상명핵심역량 교양 계산 ] domain 기초 포함 과목 수 : {}", courseWithEssential.size());

        //창의적문제 or 융복합
        long val1 = courseWithEssential.stream()
                .filter(course -> course.getDomain().contains("전문지식탐구역량") ||
                        course.getDomain().contains("창의적문제해결역량") ||
                        course.getDomain().contains("융복합역량") ||
                        course.getDomain().contains("다양성존중역량") ||
                        course.getDomain().contains("윤리실천역량"))
                .limit(2)
                .count();
        log.info("[ 상명핵심역량 교양 계산 ] 창의적문제 or 융복합 value : {}", val1);

        return (int) val1;
    }

    //균형 교양(CULTURE_S) 계산
    private static int getBalanceCultureCredits(List<CourseTemporary> courses, User user) {

        //균형 포함 domain 추출
        List<CourseTemporary> courseWithBalance = courses.stream()
                .filter(course -> course.getDomain() != null &&
                        course.getDomain().contains("균형"))
                .toList();
        log.info("[ 균형 교양 계산 ] domain 기초 포함 과목 수 : {}", courseWithBalance.size());

        //균형(인문)
        long humanities = courseWithBalance.stream()
                .filter(course -> course.getDomain().contains("인문"))
                .limit(1)
                .count();
        log.info("[ 균형 교양 계산 ] 인문 value : {}", humanities);

        //균형(사회)
        long society = courseWithBalance.stream()
                .filter(course -> course.getDomain().contains("사회"))
                .limit(1)
                .count();
        log.info("[ 균형 교양 계산 ] 사회 value : {}", society);

        //균형(자연)
        long nature = courseWithBalance.stream()
                .filter(course -> course.getDomain().contains("자연"))
                .limit(1)
                .count();
        log.info("[ 균형 교양 계산 ] 자연 value : {}", nature);

        //균형(공학)
        long engineering = courseWithBalance.stream()
                .filter(course -> course.getDomain().contains("공학"))
                .limit(1)
                .count();
        log.info("[ 균형 교양 계산 ] 공학 value : {}", engineering);

        //균형(예술)
        long art = courseWithBalance.stream()
                .filter(course -> course.getDomain().contains("예술"))
                .limit(1)
                .count();
        log.info("[ 균형 교양 계산 ] 예술 value : {}", art);

        //TODO : 예외처리
        String userType = user.getProfile().getDepartment().getType();
        switch (userType) {
            case "인문":
                return (int) (society + nature + engineering + art);
            case "사회":
                return (int) (humanities + nature + engineering + art);
            case "자연":
                return (int) (humanities + society + engineering + art);
            case "공학":
                return (int) (humanities + society + nature + art);
            case "예술":
                return (int) (humanities + society + nature + engineering);
        }
        throw new RuntimeException();
    }

    private static int getAllCredits(List<CourseTemporary> courses) {
        return courses.stream()
                .mapToInt(CourseTemporary::getCredit)
                .sum();
    }

}

