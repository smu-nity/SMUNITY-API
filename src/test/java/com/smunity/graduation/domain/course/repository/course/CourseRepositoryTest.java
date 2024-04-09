package com.smunity.graduation.domain.course.repository.course;

import com.smunity.graduation.domain.course.entity.Course;
import com.smunity.graduation.global.common.type.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static com.smunity.graduation.global.common.type.Category.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class CourseRepositoryTest {

    @Autowired
    CourseRepository courseRepository;

    String userName;

    @BeforeEach
    public void setUp() {
        //given
        userName = "201911019";
    }

    @Test
    public void findAllByUserUserNameAndSubDomainIsNotNull() throws Exception {
        //when
        List<Course> courseList = courseRepository.findAllByUserUserNameAndSubDomainIsNotNull(userName);

        //then
        courseList.stream()
                .map(Course::getSubDomain)
                .forEach(System.out::println);
    }

    @Test
    public void findByCategoryNull() throws Exception {
        //given
        int expected = 132;

        //when
        int actual = calculateCredits(userName, null);

        //then
        assertEquals(expected, actual, "전체 이수 학점 오류");
    }

    @Test
    public void findByCategoryMajorAdvanced() throws Exception {
        //given
        int expected = 18;

        //when
        int actual = calculateCredits(userName, MAJOR_ADVANCED);

        //then
        assertEquals(expected, actual, "전공 심화 이수 학점 오류");
    }

    @Test
    public void findByCategoryMajorOptional() throws Exception {
        //given
        int expected = 66;

        //when
        int actual = calculateCredits(userName, MAJOR_OPTIONAL);

        //then
        assertEquals(expected, actual, "전공 선택 이수 학점 오류");
    }

    @Test
    public void findByCategoryCulture() throws Exception {
        //given
        int expected = 42;

        //when
        int actual = calculateCredits(userName, CULTURE);

        //then
        assertEquals(expected, actual, "교양 이수 학점 오류");
    }

    private int calculateCredits(String userName, Category category) {
        List<Course> courses = courseRepository.findByUsernameAndCategory(userName, category);
        return courses.stream().mapToInt(Course::getCredit).sum();
    }
}
