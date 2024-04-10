package com.smunity.graduation.domain.subject.service;

import com.smunity.graduation.domain.subject.dto.CultureResponseDto;
import com.smunity.graduation.domain.subject.dto.ResultResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.smunity.graduation.global.common.type.SubDomain.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class CultureQueryServiceTest {

    @Autowired
    CultureQueryService cultureQueryService;

    @Test
    public void getCultures() throws Exception {
        //given
        int expected = 93;

        //when
        ResultResponseDto<CultureResponseDto> responseDto = cultureQueryService.getCultures(null);
        int actual = responseDto.count();

        //then
        assertEquals(expected, actual, "교양 전체 조회 오류");
    }

    @Test
    public void getCulturesBasicComputer() throws Exception {
        //given
        int expected = 2;

        //when
        ResultResponseDto<CultureResponseDto> responseDto = cultureQueryService.getCultures(BASIC_COMPUTER);
        int actual = responseDto.count();

        //then
        assertEquals(expected, actual, "컴퓨팅사고와문제해결 조회 오류");
    }

    @Test
    public void getCulturesCoreEthical() throws Exception {
        //given
        int expected = 4;

        //when
        ResultResponseDto<CultureResponseDto> responseDto = cultureQueryService.getCultures(CORE_ETHICAL);
        int actual = responseDto.count();

        //then
        assertEquals(expected, actual, "윤리실천역량 조회 오류");
    }

    @Test
    public void getCulturesBalanceNaturalEngineer() throws Exception {
        //given
        int expected = 12;

        //when
        ResultResponseDto<CultureResponseDto> responseDto = cultureQueryService.getCultures(BALANCE_NATURAL_ENGINEER);
        int actual = responseDto.count();

        //then
        assertEquals(expected, actual, "균형(자연/공학) 조회 오류");
    }
}
