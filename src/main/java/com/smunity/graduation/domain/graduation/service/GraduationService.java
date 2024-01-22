package com.smunity.graduation.domain.graduation.service;

import com.smunity.graduation.domain.accounts.entity.User;
import com.smunity.graduation.domain.accounts.entity.Year;
import com.smunity.graduation.domain.accounts.repository.YearJpaRepository;
import com.smunity.graduation.domain.accounts.repository.user.UserRepository;
import com.smunity.graduation.domain.graduation.dto.GraduationResponseDto;
import com.smunity.graduation.domain.graduation.repository.CourseTemporaryRepository;
import com.smunity.graduation.domain.graduation.temporary.CourseTemporary;
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
    //-------------------임시 레포지토리


    public GraduationResponseDto getGraduationCriteria() {
        //TODO : 유저 가져오기 -> SecurityContextHolder 필요
        User user = userRepository.findByUserName("201910926").orElseThrow();
        log.info("[ Graduation Service ] user name : {}", user.getUserName());

        //TODO : 학번별 졸업 요건 가져오기 -> MS Controller 필요
        // [year] major_i : 전공 심화, major_s : 전공 선택, culture : 교양, culture_cnt : ?? , all_score : 필요 이수 학점
        Year year = yearJpaRepository.findByYear(user.getUserName().substring(0, 4)).orElseThrow();

        return calculateCriteria(user, year);
    }

    private GraduationResponseDto calculateCriteria(User user, Year year) {

        //TODO : 사용자 들은 과목 조회 -> Course Entity, Repository 필요
        List<CourseTemporary> courses = courseTemporaryRepository.findAllByUser_Id(user.getId());
        return GraduationResponseDto.to(courses, year, user);
    }
}
