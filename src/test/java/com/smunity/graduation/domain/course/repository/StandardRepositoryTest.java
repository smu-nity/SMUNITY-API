package com.smunity.graduation.domain.course.repository;

import com.smunity.graduation.domain.accounts.entity.Year;
import com.smunity.graduation.domain.accounts.repository.YearJpaRepository;
import com.smunity.graduation.domain.course.entity.Standard;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.smunity.graduation.global.common.enums.Category.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class StandardRepositoryTest {

    @Autowired
    YearJpaRepository yearJpaRepository;

    @Autowired
    StandardRepository standardRepository;

    @Test
    public void findByYearAndCategory() throws Exception {
        //given
        Year year = yearJpaRepository.findByYear("2019").orElseThrow(Exception::new);

        //when
        Standard majorAdvanced = standardRepository.findByYearAndCategory(year, MAJOR_ADVANCED).orElseThrow(Exception::new);
        Standard majorOptional = standardRepository.findByYearAndCategory(year, MAJOR_OPTIONAL).orElseThrow(Exception::new);
        Standard culture = standardRepository.findByYearAndCategory(year, CULTURE).orElseThrow(Exception::new);

        //then
        assertEquals(majorAdvanced.getTotal(), 15, "전공 심화 기준 학점 오류");
        assertEquals(majorOptional.getTotal(), 45, "전공 선택 기준 학점 오류");
        assertEquals(culture.getTotal(), 33, "교양 기준 학점 오류");
    }
}
