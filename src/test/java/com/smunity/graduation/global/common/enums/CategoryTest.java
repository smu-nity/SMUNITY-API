package com.smunity.graduation.global.common.enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CategoryTest {

    @Test
    public void CategoryOfTest() throws Exception {
        //given
        Category category1 = Category.of("1전심");
        Category category2 = Category.of("1일선");

        //then
        assertEquals(Category.MAJOR_ADVANCED, category1, "1전심이 아닙니다.");
        assertEquals(Category.ETC, category2, "기타가 아닙니다.");
    }
}
