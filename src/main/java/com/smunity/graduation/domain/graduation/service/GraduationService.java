package com.smunity.graduation.domain.graduation.service;

import com.smunity.graduation.domain.accounts.entity.User;
import com.smunity.graduation.domain.accounts.entity.Year;
import com.smunity.graduation.domain.accounts.repository.YearJpaRepository;
import com.smunity.graduation.domain.accounts.repository.user.UserRepository;
import com.smunity.graduation.domain.graduation.dto.GraduationResponseDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
public class GraduationService {

    //-------------------임시 레포지토리
    UserRepository userRepository;
    YearJpaRepository yearJpaRepository;
    //-------------------임시 레포지토리


    public GraduationResponseDto getGraduationCriteria() {
        //TODO : 유저 가져오기 -> SecurityContextHolder 필요
        User user = userRepository.findByUserName("201910926").orElseThrow();

        //TODO : 학번별 졸업 요건 가져오기 -> MS Controller 필요
        Year year = yearJpaRepository.findByYear(user.getUserName().substring(0, 4)).orElseThrow();

        // [year] major_i : 전공 심화, major_s : 전공 선택, culture : 교양, culture_cnt : ?? , all_score : 필요 이수 학점

        return calculateCriteria();
    }

    private GraduationResponseDto calculateCriteria() {
        //TODO : 사용자 들은 과목 조회 -> Course Entity, Repository 필요
        return null;
    }
}
