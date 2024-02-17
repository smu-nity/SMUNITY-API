package com.smunity.graduation.domain.graduation.service;

import com.smunity.graduation.domain.accounts.entity.User;
import com.smunity.graduation.domain.accounts.entity.Year;
import com.smunity.graduation.domain.accounts.repository.YearJpaRepository;
import com.smunity.graduation.domain.accounts.repository.user.UserRepository;
import com.smunity.graduation.domain.course.entity.Course;
import com.smunity.graduation.domain.course.repository.CourseRepository;
import com.smunity.graduation.domain.graduation.dto.GraduationResponseDto;
import com.smunity.graduation.domain.graduation.dto.SubjectResponseDto;
import com.smunity.graduation.domain.graduation.entity.Culture;
import com.smunity.graduation.domain.graduation.entity.Major;
import com.smunity.graduation.domain.graduation.entity.Subject;
import com.smunity.graduation.domain.graduation.repository.CultureRepository;
import com.smunity.graduation.domain.graduation.repository.MajorRepository;
import com.smunity.graduation.domain.graduation.repository.SubjectRepository;
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

    private final UserRepository userRepository;
    private final YearJpaRepository yearJpaRepository;
    private final CourseRepository courseRepository;
    private final CultureRepository cultureRepository;
    private final MajorRepository majorRepository;
    private final SubjectRepository subjectRepository;
    private final GraduationUtil graduationUtil;

    public GraduationResponseDto getGraduationCriteria() {
        //TODO : 유저 가져오기
        User user = userRepository.findByUserName("201910926").orElseThrow();
        log.info("[ Graduation Service ] user name : {}", user.getUserName());

        //TODO : 학번별 졸업 요건 가져오기 -> MS Controller 필요
        // [year] major_i : 전공 심화, major_s : 전공 선택, culture : 교양, culture_cnt : 기초 교양 이수 개수 , all_score : 필요 이수 학점
        Year year = user.getYear();

        return calculateCriteria(user, year);
    }

    /**
     * [ 추천 과목 조회 ]
     *
     * @Param type : major_i(전공심화), major_s(전공선택), culture(일반교양), culture_b(기초교양), culture_e(핵심교양), culture_s(균형교양)
     * @Param credit : 부족 학점 (0일 수 있음)
     */
    public List<SubjectResponseDto> getRecommendSubjects(String type, int credit) {
        //TODO : 유저 가져오기 -> SecurityContextHolder 필요
        User user = userRepository.findByUserName("201900000").orElseThrow();
        log.info("[ Graduation Service ] user name : {}", user.getUserName());

        List<Course> courses = courseRepository.findAllByUser_Id(user.getId());

        log.info("[ Recommend Subject ] type --> {}", type);
        //결과 리스트
        List<SubjectResponseDto> result = new ArrayList<>();

        switch (type) {
            case "major_i":
                //전공심화인 경우
                log.info("[ Recommend Subject ] 전공 심화 검색");

                //TODO : 학년과 학기 User에서 가져오기 + 로직에 추가
                result.addAll(
                        graduationUtil.getRecommendMajorSubjects
                                ("major_i", "1학년", "1학기", user.getDepartment().getName())
                );
                break;
            case "major_s":
                //전공선택인 경우
                log.info("[ Recommend Subject ] 전공 선택 검색");

                //TODO : 학년과 학기 User에서 가져오기
                result.addAll(
                        graduationUtil.getRecommendMajorSubjects
                                ("major_s", "1학년", "1학기", user.getDepartment().getName())
                );
                break;
            case "culture":
                //교양인 경우
                log.info("[ Recommend Subject ] 일반 교양 검색");

                result.addAll(graduationUtil.getRecommendCultureSubjects
                        ("culture", credit, null, null, courses));
                break;
            case "culture_b":
                //기초교양인 경우
                log.info("[ Recommend Subject ] 기초 교양 검색");

                // 기초교양 검사 1. 사고와 표현 검사
                if (courses.stream()
                        .filter(course -> course.getDomain() != null) // domain이 null 아닌 과목 조회 -> domain이 없는 전공이 걸러짐
                        .noneMatch(course -> course.getDomain().contains("사고와표현"))) { // domain에 사고와표현 있는 과목이 있는지?
                    log.info("[ Recommend Subject ] 사고와 표현 과목 필요"); // 아무 과목도 조회되지 않았을 경우

                    //사고와 표현 추천 과목 가져오기
                    result.addAll(graduationUtil.getRecommendCultureSubjects
                            ("culture_b", credit, "기초", "사고와표현", null));
                }

                // 기초교양 검사 2. EnglishforAcademicPurposes or 기초수학 검사 (둘 중 하나 이수 필요함)
                if (courses.stream()
                        .filter(course -> course.getDomain() != null)
                        .noneMatch(course -> course.getDomain().contains("EnglishFoundation") || // 기초교양 1. EnglishforAcademicPurposes
                                course.getDomain().contains("영어1") || // EnglishFoundation
                                course.getDomain().contains("영어2") || // EnglishFoundation
                                course.getDomain().contains("기초수학") ||
                                course.getDomain().contains("기초미적분학") // 기초교양 2. 기초수학 개편 전
                        )) {

                    log.info("[ Recommend Subject ] English or 기초수학 과목 필요"); //조회된 과목 없음 -> 이수해야함

                    //EnglishforAcademicPurposes 추천 과목 가져오기
                    result.addAll(graduationUtil.getRecommendCultureSubjects
                            ("culture_b", credit, "기초", "EnglishforAcademicPurposes", null));

                    //기초수학 추천 과목 가져오기
                    result.addAll(graduationUtil.getRecommendCultureSubjects
                            ("culture_b", credit, "기초", "기초수학", null));
                }

                //기초교양 검사 3. 컴퓨팅사고 or 알고리즘과게임 :
                // 18, 19학번의 경우 둘 중 하나 이수
                // 20학번 부터는 각각 따로 이수 필요
                if (user.getUserName().startsWith("2019") || user.getUserName().startsWith("2018")) { //18학번 또는 19학번일 경우
                    if (courses.stream()
                            .filter(course -> course.getDomain() != null)
                            .noneMatch(course -> course.getDomain().contains("컴퓨팅사고와데이터의이해") ||
                                    course.getDomain().contains("컴퓨팅사고와게임디자인") || // 컴퓨팅사고와데이터의이해 개편 전
                                    course.getDomain().contains("컴퓨팅사고와문제해결") || // 컴퓨팅사고와데이터의이해 개편 전
                                    course.getDomain().contains("알고리즘과게임콘텐츠")
                            )) {

                        log.info("[ Recommend Subject ] 18,19학번 : 컴퓨팅 or 알고리즘 과목 필요");

                        //컴퓨팅 추천 과목 조회
                        result.addAll(graduationUtil.getRecommendCultureSubjects
                                ("culture_b", credit, "기초", "컴퓨팅사고와데이터의이해", null));

                        //알고리즘 추천 과목 조회
                        result.addAll(graduationUtil.getRecommendCultureSubjects
                                ("culture_b", credit, "기초", "알고리즘과게임콘텐츠", null));
                    }
                } else { // 18, 19학번이 아닌 경우
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
                //5개 영역 중 사용자가 듣지 않은 영역을 추천해서 반환, 2개 이상 들었으면 빈 리스트를 반환
                String[] cultures_essential = {"전문지식탐구역량", "창의적문제해결역량", "융복합역량", "다양성존중역량", "윤리실천역량"};
                List<String> essentialCultures = new ArrayList<>(Arrays.asList(cultures_essential));
                List<String> excludeEssentialSubjects = new ArrayList<>(); //이수 영역 5개 리스트

                essentialCultures
                        .forEach(essentialCultureDomain -> { // 이수 영역 5개를 Iterate
                            if (courses.stream()
                                    .filter(course -> course.getDomain() != null)
                                    .anyMatch(course -> course.getDomain().contains("핵심") &&
                                            course.getDomain().contains(essentialCultureDomain)
                                    )
                            ) {
                                excludeEssentialSubjects.add(essentialCultureDomain); //이수한 영역 리스트에 저장
                            }
                        });

                //essentialCultures 에서 사용자가 이수한 영역 제외
                excludeEssentialSubjects.forEach(essentialCultures::remove);

                //2개가 충족되지 않았을 경우 (들은 영역 제외가 4개 이상일 경우)
                if (essentialCultures.size() >= 4) {
                    essentialCultures
                            .forEach(balanceCultureDomain -> {
                                //이수하지 않은 과목 추천 검색
                                result.addAll(graduationUtil.getRecommendCultureSubjects
                                        ("culture_e", credit, "핵심", balanceCultureDomain, null));
                            });
                } else log.info("[ 교양 추천 ] 모든 상명핵심역량 교양을 만족했습니다."); //모든 영역을 들었을 경우
                break;

            case "culture_s":
                //균형교양인 경우
                //사용자가 들어야 하는 영역 제외 4개 중 사용자가 듣지 않은 영역 추천 과목들을 반환, 3개 이상 들었으면 빈 리스트를 반환
                String[] cultures_balance = {"인문", "사회", "자연", "공학", "예술"};
                List<String> balanceCultures = new ArrayList<>(Arrays.asList(cultures_balance));
                List<String> excludeBalanceSubjects = new ArrayList<>();


                String userType = user.getDepartment().getType();
                balanceCultures.remove(userType); //사용자 영역 제외

                balanceCultures
                        .forEach(balanceCultureDomain -> {
                            if (courses.stream()
                                    .filter(course -> course.getDomain() != null)
                                    .anyMatch(course -> course.getDomain().contains("균형") &&
                                            course.getDomain().contains(balanceCultureDomain))) {
                                excludeBalanceSubjects.add(balanceCultureDomain); //이수한 영역 리스트에 저장
                            }
                        });

                //사용자가 이수한 영역 빼기
                excludeBalanceSubjects.forEach(balanceCultures::remove);

                //3개가 충족되지 않았을 경우 (들은 영역 제외가 3개 이상일 경우)
                if (balanceCultures.size() >= 3) {
                    balanceCultures
                            .forEach(balanceCultureDomain -> {
                                //이수하지 않은 영역 추천 과목 조회
                                result.addAll(graduationUtil.getRecommendCultureSubjects
                                        ("culture_s", credit, "균형", balanceCultureDomain, null));
                            });
                } else log.info("[ 교양 추천 ] 모든 균형 교양을 만족했습니다."); //모든 영역을 이수했을 경우
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
        List<Course> courses = courseRepository.findAllByUser_Id(user.getId());
        return GraduationResponseDto.to(courses, year, user, subjectRepository);
    }
}
