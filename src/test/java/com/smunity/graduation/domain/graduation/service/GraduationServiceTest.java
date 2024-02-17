package com.smunity.graduation.domain.graduation.service;

import com.smunity.graduation.domain.graduation.dto.GraduationResponseDto;
import com.smunity.graduation.domain.graduation.dto.SubjectResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class GraduationServiceTest {

    @Autowired
    private GraduationService graduationService;
    private String username;

    @BeforeEach
    public void setUp() {
        //given
        username = "201910926";
    }

    @Test
    public void getGraduationCriteriaTest() {

        GraduationResponseDto graduationCriteria = graduationService.getGraduationCriteria(username);

        System.out.println("[ 졸업요건 검사 테스트 ] 테스트 완료 : " + graduationCriteria.result());

    }

    @Test
    public void getRecommendMajorISubjects() {

        List<SubjectResponseDto> result = graduationService.getRecommendSubjects("major_i", 3, username);

        System.out.println("[ 전공 심화 추천 테스트 ] 테스트 완료 : \n" + result);
    }


    @Test
    public void getRecommendMajorSSubjects() {

        List<SubjectResponseDto> result = graduationService.getRecommendSubjects("major_s", 3, username);

        System.out.println("[ 전공 선택 추천 테스트 ] 테스트 완료 : \n" + result);
    }

    @Test
    public void getRecommendCultureSubjects() {

        List<SubjectResponseDto> result = graduationService.getRecommendSubjects("culture", 3, username);

        System.out.println("[ 교양 추천 테스트 ] 테스트 완료 : \n" + result);
    }

    @Test
    public void getRecommendCultureBSubjects() {

        List<SubjectResponseDto> result = graduationService.getRecommendSubjects("culture_b", 3, username);

        System.out.println("[ 기초 교양 추천 테스트 ] 테스트 완료 : \n" + result);
    }

    @Test
    public void getRecommendCultureESubjects() {

        List<SubjectResponseDto> result = graduationService.getRecommendSubjects("culture_e", 3, username);

        System.out.println("[ 상명 핵심 역량 교양 추천 테스트 ] 테스트 완료 : \n" + result);
    }

    @Test
    public void getRecommendCultureSSubjects() {

        List<SubjectResponseDto> result = graduationService.getRecommendSubjects("culture_s", 3, username);

        System.out.println("[ 균형 교양 추천 테스트 ] 테스트 완료 : \n" + result);
    }

}
