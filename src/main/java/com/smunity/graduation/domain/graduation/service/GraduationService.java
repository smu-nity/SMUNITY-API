package com.smunity.graduation.domain.graduation.service;

import com.smunity.graduation.domain.accounts.entity.User;
import com.smunity.graduation.domain.accounts.entity.Year;
import com.smunity.graduation.domain.accounts.repository.YearJpaRepository;
import com.smunity.graduation.domain.accounts.repository.user.UserRepository;
import com.smunity.graduation.domain.graduation.dto.GraduationResponseDto;
import com.smunity.graduation.domain.graduation.dto.SubjectResponseDto;
import com.smunity.graduation.domain.graduation.entity.Culture;
import com.smunity.graduation.domain.graduation.entity.Major;
import com.smunity.graduation.domain.graduation.entity.Subject;
import com.smunity.graduation.domain.graduation.repository.CourseTemporaryRepository;
import com.smunity.graduation.domain.graduation.repository.CultureRepository;
import com.smunity.graduation.domain.graduation.repository.MajorRepository;
import com.smunity.graduation.domain.graduation.repository.SubjectRepository;
import com.smunity.graduation.domain.graduation.temporary.CourseTemporary;
import com.smunity.graduation.domain.graduation.utils.GraduationUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class GraduationService {

    //-------------------임시 레포지토리
    private final UserRepository userRepository;
    private final YearJpaRepository yearJpaRepository;
    private final CourseTemporaryRepository courseTemporaryRepository;
    private final CultureRepository cultureRepository;
    private final MajorRepository majorRepository;
    //-------------------임시 레포지토리

    private final SubjectRepository subjectRepository;

    private final GraduationUtil graduationUtil;


    public GraduationResponseDto getGraduationCriteria() {
        //TODO : 유저 가져오기 -> SecurityContextHolder 필요
        User user = userRepository.findByUserName("201910926").orElseThrow();
        log.info("[ Graduation Service ] user name : {}", user.getUserName());

        //TODO : 학번별 졸업 요건 가져오기 -> MS Controller 필요
        // [year] major_i : 전공 심화, major_s : 전공 선택, culture : 교양, culture_cnt : ?? , all_score : 필요 이수 학점
        Year year = yearJpaRepository.findByYear(user.getUserName().substring(0, 4)).orElseThrow();

        return calculateCriteria(user, year);
    }

    public List<SubjectResponseDto> getRecommendSubjects(String type, int credit) {
        //TODO : 유저 가져오기 -> SecurityContextHolder 필요
        User user = userRepository.findByUserName("201910926").orElseThrow();
        log.info("[ Graduation Service ] user name : {}", user.getUserName());

        List<CourseTemporary> courses = courseTemporaryRepository.findAllByUser_Id(user.getId());

        List<SubjectResponseDto> result = new ArrayList<>();
        log.info("[ Recommend Subject ] type --> {}", type);
        switch (type) {
            case "major_i":
                //전공심화인 경우
                //TODO : 학년과 학기 User에서 가져오기
                result.addAll(
                        graduationUtil.getRecommendMajorSubjects
                                ("major_i", "1학년", "1학기", user.getDepartment().getName())
                );
                break;
            case "major_s":
                //전공선택인 경우
                //TODO : 학년과 학기 User에서 가져오기
                result.addAll(
                        graduationUtil.getRecommendMajorSubjects
                                ("major_s", "1학년", "1학기", user.getDepartment().getName())
                );
                break;
            case "culture":
                log.info("[ Recommend Subject ] 일반 교양 검색");
                //교양인 경우
                result.addAll(graduationUtil.getRecommendCultureSubjects
                        ("culture", credit, null, null, courses));
                break;
            case "culture_b":
                //기초교양인 경우
                log.info("[ Recommend Subject ] 기초 교양 검색");

                //사고와 표현 검사
                if (courses.stream()
                        .filter(course -> course.getDomain() != null)
                        .noneMatch(course -> course.getDomain().contains("사고와표현"))) {
                    log.info("[ Recommend Subject ] 사고와 표현 과목 필요");
                    result.addAll(graduationUtil.getRecommendCultureSubjects
                            ("culture_b", credit, "기초", "사고와표현", null));
                }

                //EnglishforAcademicPurposes or 기초수학 검사
                if (courses.stream()
                        .filter(course -> course.getDomain() != null)
                        .noneMatch(course -> course.getDomain().contains("EnglishFoundation") || // 기초교양 1. EnglishforAcademicPurposes
                                course.getDomain().contains("영어1") || // EnglishFoundation
                                course.getDomain().contains("영어2") || // EnglishFoundation
                                course.getDomain().contains("기초수학") ||
                                course.getDomain().contains("기초미적분학") // 기초교양 2. 기초수학 개편 전
                        )) {
                    log.info("[ Recommend Subject ] English or 기초수학 과목 필요");
                    result.addAll(graduationUtil.getRecommendCultureSubjects
                            ("culture_b", credit, "기초", "EnglishforAcademicPurposes", null));
                    result.addAll(graduationUtil.getRecommendCultureSubjects
                            ("culture_b", credit, "기초", "기초수학", null));
                }

                //컴퓨팅사고 or 알고리즘과게임 : 18, 19학번의 경우 택1, 20학번 부터는 각각 따로 이수 필요
                if (user.getUserName().startsWith("2019") || user.getUserName().startsWith("2018")) {
                    if (courses.stream()
                            .filter(course -> course.getDomain() != null)
                            .noneMatch(course -> course.getDomain().contains("컴퓨팅사고와데이터의이해") ||
                                    course.getDomain().contains("컴퓨팅사고와게임디자인") || // 컴퓨팅사고와데이터의이해 개편 전
                                    course.getDomain().contains("컴퓨팅사고와문제해결") || // 컴퓨팅사고와데이터의이해 개편 전
                                    course.getDomain().contains("알고리즘과게임콘텐츠")
                            )) {
                        log.info("[ Recommend Subject ] 18,19학번 : 컴퓨팅 or 알고리즘 과목 필요");
                        result.addAll(graduationUtil.getRecommendCultureSubjects
                                ("culture_b", credit, "기초", "컴퓨팅사고와데이터의이해", null));
                        result.addAll(graduationUtil.getRecommendCultureSubjects
                                ("culture_b", credit, "기초", "알고리즘과게임콘텐츠", null));
                    }
                } else {
                    if (courses.stream()
                            .filter(course -> course.getDomain() != null)
                            .noneMatch(course -> course.getDomain().contains("컴퓨팅사고와데이터의이해") ||
                                    course.getDomain().contains("컴퓨팅사고와게임디자인") || // 컴퓨팅사고와데이터의이해 개편 전
                                    course.getDomain().contains("컴퓨팅사고와문제해결") // 컴퓨팅사고와데이터의이해 개편 전
                            )) {
                        log.info("[ Recommend Subject ] 20번 이상 : 컴퓨팅 과목 필요");
                        result.addAll(graduationUtil.getRecommendCultureSubjects
                                ("culture_b", credit, "기초", "컴퓨팅사고와데이터의이해", null));
                    }

                    if (courses.stream()
                            .filter(course -> course.getDomain() != null)
                            .noneMatch(course -> course.getDomain().contains("알고리즘과게임콘텐츠")
                            )) {
                        log.info("[ Recommend Subject ] 20학번 이상 : 알고리즘 과목 필요");
                        result.addAll(graduationUtil.getRecommendCultureSubjects
                                ("culture_b", credit, "기초", "알고리즘과게임콘텐츠", null));
                    }
                }
                break;

            case "culture_e":
                //상명핵심역량교양인 경우
                //5개 영역 중 사용자가 듣지 않은 영역을 반환, 2개 이상 들었으면 빈 리스트를 반환
                String[] cultures_essential = {"전문지식탐구역량", "창의적문제해결역량", "융복합역량", "다양성존중역량", "윤리실천역량"};
                List<String> essentialCultures = new ArrayList<>(Arrays.asList(cultures_essential));

                essentialCultures.stream()
                        .forEach(essentialCultureDomain -> {
                            if (courses.stream()
                                    .filter(course -> course.getDomain() != null)
                                    .anyMatch(course -> course.getDomain().contains("핵심") &&
                                            course.getDomain().contains(essentialCultureDomain))) {
                                essentialCultures.remove(essentialCultureDomain); //사용자가 들은 영역 제외
                            }
                        });

                //2개가 충족되지 않았을 경우 (들은 영역 제외가 2개 이상일 경우)
                if (essentialCultures.size() >= 4) {
                    essentialCultures
                            .forEach(balanceCultureDomain -> {
                                result.addAll(graduationUtil.getRecommendCultureSubjects
                                        ("culture_e", credit, "핵심", balanceCultureDomain, null));
                            });
                }
                break;

            case "culture_s":
                //균형교양인 경우
                //사용자가 들어야 하는 영역 제외 4개 중 사용자가 듣지 않은 영역 과목들을 반환, 3개 이상 들었으면 빈 리스트를 반환
                String[] cultures_balance = {"인문", "사회", "자연", "공학", "예술"};
                List<String> balanceCultures = new ArrayList<>(Arrays.asList(cultures_balance));

                String userType = user.getDepartment().getType();
                balanceCultures.remove(userType); //사용자 영역 제외

                balanceCultures
                        .forEach(balanceCultureDomain -> {
                            if (courses.stream()
                                    .filter(course -> course.getDomain() != null)
                                    .anyMatch(course -> course.getDomain().contains("균형") &&
                                            course.getDomain().contains(balanceCultureDomain))) {
                                balanceCultures.remove(balanceCultureDomain); //사용자가 들은 영역 제외
                            }
                        });

                //3개가 충족되지 않았을 경우 (들은 영역 제외가 2개 이상일 경우)
                if (balanceCultures.size() >= 2) {
                    balanceCultures
                            .forEach(balanceCultureDomain -> {

                                result.addAll(graduationUtil.getRecommendCultureSubjects
                                        ("culture_s", credit, "균형", balanceCultureDomain, null));
                            });
                }
                break;

        }

        return result;
    }

    //일회성 Culture, Major -> Subject 통합용
    @Transactional
    public void organizeSubjects() {
        //Culture
        List<Culture> cultures = cultureRepository.findAll();
        cultures
                .forEach(culture -> {
//                    log.info("[ Subject 통합 - Culture ] culture  ID : {}", culture.getId());
                    Subject subject = subjectRepository.findById(culture.getSubject().getId()).orElseThrow();
//                    log.info("[ Subject 통합 - Culture ] found Subject : {}", subject.getName());
                    if (culture.getType().equals(subject.getType())) {
                        subject.setGrade("전체학년");
                        subject.setSemester("전체학기");
                        subject.setDomain(culture.getCultureType());
                        subject.setSubDomain(culture.getCultureSubType());
                    } else {
                        log.error("[ Subject 통합 - Culture ] ERROR : Type이 맞지 않음 [{}] Culture -> {}, Subject -> {}", subject.getName(), culture.getType(), subject.getType());
                    }
                });

        //Major
        List<Major> majors = majorRepository.findAll();
        majors
                .forEach(major -> {
//                    log.info("[ Subject 통합 - Major ] major  ID : {}", major.getId());
                    Subject subject = subjectRepository.findById(major.getSubject().getId()).orElseThrow();
//                    log.info("[ Subject 통합 - Major ] found Subject : {}", subject.getName());
                    if (major.getType().equals(subject.getType())) {
                        subject.setGrade(major.getGrade());
                        subject.setSemester(major.getSemester());
                    } else {
                        log.error("[ Subject 통합 - Major ] ERROR : Type이 맞지 않음 [{}] Major -> {}, Subject -> {}", subject.getName(), major.getType(), subject.getType());
                    }
                });
    }

    private GraduationResponseDto calculateCriteria(User user, Year year) {

        //TODO : 사용자 들은 과목 조회 -> Course Entity, Repository 필요
        List<CourseTemporary> courses = courseTemporaryRepository.findAllByUser_Id(user.getId());
        return GraduationResponseDto.to(courses, year, user, subjectRepository);
    }

}