package com.smunity.graduation.domain.course.service;

import com.smunity.graduation.domain.course.dto.ResultResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.smunity.graduation.global.common.enums.Category.*;
import static com.smunity.graduation.global.common.enums.Domain.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class CourseQueryServiceTest {

    @Autowired
    CourseQueryService courseQueryService;

    private String userName;

    @BeforeEach
    public void setUp() {
        //given
        userName = "201911019";
    }

    @Test
    public void getCoursesByCategoryNull() throws Exception {
        //given
        int expected = 132;

        //when
        ResultResponseDto responseDto = courseQueryService.getCourses(userName, null);
        int actual = responseDto.status().completed();

        //then
        assertEquals(expected, actual, "전체 이수 학점 오류");
    }

    @Test
    public void getCoursesByCategoryMajorAdvanced() throws Exception {
        //given
        int expected = 18;

        //when
        ResultResponseDto responseDto = courseQueryService.getCourses(userName, MAJOR_ADVANCED);
        int actual = responseDto.status().completed();

        //then
        assertEquals(expected, actual, "전공 심화 이수 학점 오류");
    }

    @Test
    public void getCoursesByCategoryMajorOptional() throws Exception {
        //given
        int expected = 66;

        //when
        ResultResponseDto responseDto = courseQueryService.getCourses(userName, MAJOR_OPTIONAL);
        int actual = responseDto.status().completed();

        //then
        assertEquals(expected, actual, "전공 선택 이수 학점 오류");
    }

    @Test
    public void getCoursesByCategoryCulture() throws Exception {
        //given
        int expected = 42;

        //when
        ResultResponseDto responseDto = courseQueryService.getCourses(userName, CULTURE);
        int actual = responseDto.status().completed();

        //then
        assertEquals(expected, actual, "교양 이수 학점 오류");
    }

    @Test
    public void getCultureCoursesByDomainBasic() throws Exception {
        //when
        ResultResponseDto resultResponseDto = courseQueryService.getCultureCourses(userName, BASIC);

        //then
        System.out.println(resultResponseDto);
    }

    @Test
    public void getCultureCoursesByDomainCore() throws Exception {
        //when
        ResultResponseDto resultResponseDto = courseQueryService.getCultureCourses(userName, CORE);

        //then
        System.out.println(resultResponseDto);
    }

    @Test
    public void getCultureCoursesByDomainBalance() throws Exception {
        //when
        ResultResponseDto resultResponseDto = courseQueryService.getCultureCourses(userName, BALANCE);

        //then
        System.out.println(resultResponseDto);
    }
}
