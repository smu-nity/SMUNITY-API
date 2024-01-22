package com.smunity.graduation.domain.course.repository;

import com.smunity.graduation.domain.course.entity.Course;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class CourseRepositoryTest {
    @Autowired
    CourseRepository courseRepository;

    @Test
    public void findAllTest() throws Exception {
        //when
        List<Course> courseList = courseRepository.findAll();

        //then
        for (Course course : courseList) {
            System.out.println(course);
        }
    }
}
