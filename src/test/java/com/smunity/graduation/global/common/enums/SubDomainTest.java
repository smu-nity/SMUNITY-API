package com.smunity.graduation.global.common.enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SubDomainTest {

    @Test
    public void SubDomainOfTest() throws Exception {
        //given
        SubDomain subDomain1 = SubDomain.of("기초(영어2)");
        SubDomain subDomain2 = SubDomain.of("컴퓨팅사고와문제해결");
        SubDomain subDomain3 = SubDomain.of("핵심(다양성존중역량)");
        SubDomain subDomain4 = SubDomain.of("균형(공학)");

        //then
        assertEquals(SubDomain.BASIC_ENG_MATH, subDomain1, "기초(영어2)이 아닙니다.");
        assertEquals(SubDomain.BASIC_COMPUTER, subDomain2, "컴퓨팅사고와문제해결이 아닙니다.");
        assertEquals(SubDomain.CORE_DIVERSITY, subDomain3, "핵심(다양성존중역량)이 아닙니다.");
        assertEquals(SubDomain.BALANCE_ENGINEER, subDomain4, "균형(공학)이 아닙니다.");
    }
}
