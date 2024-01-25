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

        switch (type) {
            case "major_i":
                //전공심화인 경우

            case "major_s":
                //전공선택인 경우

            case "culture":
                //교양인 경우
                return graduationUtil.getRecommendCultureSubjects
                        ("culture", credit, null, null, courses);
            case "culture_b":
                //기초교양인 경우
                return graduationUtil.getRecommendCultureSubjects
                        ("culture_b", credit, null, null, courses);
            case "culture_e":
                //상명핵심역량교양인 경우
                return graduationUtil.getRecommendCultureSubjects
                        ("culture_e", credit, null, null, courses);
            case "culture_s":
                //균형교양인 경우
                return graduationUtil.getRecommendCultureSubjects
                        ("culture_s", credit, null, null, courses);
        }
        throw new RuntimeException(); //type 잘못됐을 경우
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
