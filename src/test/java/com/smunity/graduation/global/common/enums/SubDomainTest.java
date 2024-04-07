package com.smunity.graduation.global.common.enums;

import org.junit.jupiter.api.Test;

import static com.smunity.graduation.global.common.enums.SubDomain.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class SubDomainTest {

    @Test
    public void SubDomainOfBasicEngMath() throws Exception {
        //given
        SubDomain subDomain = of("기초(영어2)");

        //then
        assertEquals(BASIC_ENG_MATH, subDomain, "기초(영어2)이 아닙니다.");
    }

    @Test
    public void SubDomainOfBasicComputer() throws Exception {
        //given
        SubDomain subDomain = of("컴퓨팅사고와문제해결");

        //then
        assertEquals(BASIC_COMPUTER, subDomain, "컴퓨팅사고와문제해결이 아닙니다.");
    }

    @Test
    public void SubDomainOfCoreDiversity() throws Exception {
        //given
        SubDomain subDomain = of("핵심(다양성존중역량)");

        //then
        assertEquals(CORE_DIVERSITY, subDomain, "핵심(다양성존중역량)이 아닙니다.");
    }

    @Test
    public void SubDomainOfBalanceEngineer() throws Exception {
        //given
        SubDomain subDomain = of("균형(공학)");

        //then
        assertEquals(BALANCE_ENGINEER, subDomain, "균형(공학)이 아닙니다.");
    }

    @Test
    public void SubDomainOfNull() throws Exception {
        //given
        SubDomain subDomain = of("일반(예술과디자인)");

        //then
        assertNull(subDomain, "null 이 아닙니다.");
    }
}
