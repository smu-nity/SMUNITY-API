package com.smunity.graduation.domain.course.repository;

import com.smunity.graduation.domain.accounts.entity.Year;
import com.smunity.graduation.domain.accounts.repository.YearJpaRepository;
import com.smunity.graduation.domain.course.entity.Standard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.smunity.graduation.global.common.type.Category.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class StandardRepositoryTest {

    @Autowired
    StandardRepository standardRepository;

    @Autowired
    YearJpaRepository yearJpaRepository;

    Year year;

    @BeforeEach
    public void setUp() throws Exception {
        //given
        year = yearJpaRepository.findByName("2019").orElseThrow(Exception::new);
    }

    @Test
    public void findByYearAndCategoryMajorAdvanced() throws Exception {
        //when
        Standard majorAdvanced = standardRepository.findByYearAndCategory(year, MAJOR_ADVANCED).orElseThrow(Exception::new);

        //then
        assertEquals(majorAdvanced.getTotal(), 15, "전공 심화 기준 학점 오류");
    }

    @Test
    public void findByYearAndCategoryMajorOptional() throws Exception {
        //when
        Standard majorOptional = standardRepository.findByYearAndCategory(year, MAJOR_OPTIONAL).orElseThrow(Exception::new);

        //then
        assertEquals(majorOptional.getTotal(), 45, "전공 선택 기준 학점 오류");
    }

    @Test
    public void findByYearAndCategoryCulture() throws Exception {
        //when
        Standard culture = standardRepository.findByYearAndCategory(year, CULTURE).orElseThrow(Exception::new);

        //then
        assertEquals(culture.getTotal(), 33, "교양 기준 학점 오류");
    }
}
