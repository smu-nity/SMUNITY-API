package com.smunity.graduation.domain.subject.repository.major;

import com.smunity.graduation.domain.accounts.entity.Department;
import com.smunity.graduation.domain.accounts.repository.DepartmentJpaRepository;
import com.smunity.graduation.domain.subject.entity.Major;
import com.smunity.graduation.global.common.type.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static com.smunity.graduation.global.common.type.Category.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class MajorQueryRepositoryTest {

    @Autowired
    MajorQueryRepository majorQueryRepository;

    @Autowired
    DepartmentJpaRepository departmentJpaRepository;

    Department department;

    @BeforeEach
    public void setUp() throws Exception {
        //given
        department = departmentJpaRepository.findByName("컴퓨터과학전공").orElseThrow(Exception::new);
    }

    @Test
    public void findByDepartmentAndCategory() throws Exception {
        //when
        List<Major> majors = majorQueryRepository.findByDepartmentAndCategory(department, null);

        //then
        majors.forEach(major -> System.out.println(major.getCategory()));
    }

    @Test
    public void findByDepartmentAndCategoryMajorAdvanced() throws Exception {
        //given
        Category category = MAJOR_ADVANCED;

        //when
        List<Major> majors = majorQueryRepository.findByDepartmentAndCategory(department, category);

        //then
        majors.forEach(major -> assertEquals(major.getCategory(), category));
    }

    @Test
    public void findByDepartmentAndCategoryMajorOptional() throws Exception {
        //given
        Category category = MAJOR_OPTIONAL;

        //when
        List<Major> majors = majorQueryRepository.findByDepartmentAndCategory(department, category);

        //then
        majors.forEach(major -> assertEquals(major.getCategory(), category));
    }

    @Test
    public void findByDepartmentAndCategoryEtc() throws Exception {
        //given
        Category category = ETC;

        //when
        List<Major> majors = majorQueryRepository.findByDepartmentAndCategory(department, category);

        //then
        majors.forEach(major -> assertEquals(major.getCategory(), category));
    }
}
