package com.smunity.graduation.domain.course.repository.course;

import com.smunity.graduation.domain.course.entity.Course;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static com.smunity.graduation.global.common.enums.Category.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class CourseRepositoryTest {

    @Autowired
    CourseRepository courseRepository;

    private static int cal(List<Course> courses) {
        return courses.stream().mapToInt(Course::getCredit).sum();
    }

    @Test
    public void findAllTest() throws Exception {
        //when
        List<Course> courseList = courseRepository.findAll();

        //then
        for (Course course : courseList) {
            System.out.println(course);
        }
    }

    @Test
    public void findByCategory() throws Exception {
        //given
        String userName = "201911019";

        //when
        List<Course> all = courseRepository.findByUsernameAndCategory(userName, null);
        List<Course> majorAdvanced = courseRepository.findByUsernameAndCategory(userName, MAJOR_ADVANCED);
        List<Course> majorOptional = courseRepository.findByUsernameAndCategory(userName, MAJOR_OPTIONAL);
        List<Course> culture = courseRepository.findByUsernameAndCategory(userName, CULTURE);

        //then
        assertEquals(132, cal(all), "전체 이수 학점 오류");
        assertEquals(18, cal(majorAdvanced), "전공 심화 이수 학점 오류");
        assertEquals(66, cal(majorOptional), "전공 선택 이수 학점 오류");
        assertEquals(42, cal(culture), "교양 이수 학점 오류");
    }
}
