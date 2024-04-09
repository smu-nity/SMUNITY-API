package com.smunity.graduation.global.common.type;

import org.junit.jupiter.api.Test;

import static com.smunity.graduation.global.common.type.Category.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CategoryTest {

    @Test
    public void CategoryOfMajorAdvanced() throws Exception {
        //given
        Category category = of("1전심");

        //then
        assertEquals(MAJOR_ADVANCED, category, "1전심이 아닙니다.");
    }

    @Test
    public void CategoryOfMajorOptional() throws Exception {
        //given
        Category category = of("1전선");

        //then
        assertEquals(MAJOR_OPTIONAL, category, "1전선이 아닙니다.");
    }

    @Test
    public void CategoryOfMajorTeach() throws Exception {
        //given
        Category category = of("1교직");

        //then
        assertEquals(MAJOR_OPTIONAL, category, "1전선이 아닙니다.");
    }

    @Test
    public void CategoryOfCulture() throws Exception {
        //given
        Category category = of("교필");

        //then
        assertEquals(CULTURE, category, "교양이 아닙니다.");
    }

    @Test
    public void CategoryOfCultureOptional() throws Exception {
        //given
        Category category = of("교선");

        //then
        assertEquals(CULTURE, category, "교양이 아닙니다.");
    }

    @Test
    public void CategoryOfEtc() throws Exception {
        //given
        Category category = of("1일선");

        //then
        assertEquals(ETC, category, "기타가 아닙니다.");
    }
}
