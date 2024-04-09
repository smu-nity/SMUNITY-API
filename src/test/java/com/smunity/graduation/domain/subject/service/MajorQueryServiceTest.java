package com.smunity.graduation.domain.subject.service;

import com.smunity.graduation.domain.subject.dto.ResultResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.smunity.graduation.global.common.type.Category.MAJOR_ADVANCED;
import static com.smunity.graduation.global.common.type.Category.MAJOR_OPTIONAL;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class MajorQueryServiceTest {

    @Autowired
    MajorQueryService majorQueryService;

    String userName;

    @BeforeEach
    public void setUp() throws Exception {
        //given
        userName = "201911019";
    }

    @Test
    public void getMajors() throws Exception {
        //given
        int expected = 38;

        //when
        ResultResponseDto responseDto = majorQueryService.getMajors(userName, null);
        int actual = responseDto.count();

        //then
        assertEquals(expected, actual, "전공 전체 조회 오류");
    }

    @Test
    public void getMajorsByCategoryMajorAdvanced() throws Exception {
        //given
        int expected = 14;

        //when
        ResultResponseDto responseDto = majorQueryService.getMajors(userName, MAJOR_ADVANCED);
        int actual = responseDto.count();

        //then
        assertEquals(expected, actual, "전공 심화 조회 오류");
    }

    @Test
    public void getMajorsByCategoryMajorOptional() throws Exception {
        //given
        int expected = 24;

        //when
        ResultResponseDto responseDto = majorQueryService.getMajors(userName, MAJOR_OPTIONAL);
        int actual = responseDto.count();

        //then
        assertEquals(expected, actual, "전공 선택 조회 오류");
    }
}
