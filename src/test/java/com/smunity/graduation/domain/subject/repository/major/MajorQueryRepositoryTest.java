package com.smunity.graduation.domain.subject.repository.major;

import com.smunity.graduation.domain.accounts.entity.User;
import com.smunity.graduation.domain.accounts.repository.user.UserRepository;
import com.smunity.graduation.domain.subject.entity.Major;
import com.smunity.graduation.global.common.type.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.smunity.graduation.global.common.type.Category.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional(readOnly = true)
class MajorQueryRepositoryTest {

    @Autowired
    MajorQueryRepository majorQueryRepository;

    @Autowired
    UserRepository userRepository;

    User user;

    @BeforeEach
    public void setUp() throws Exception {
        //given
        String userName = "201911019";
        user = userRepository.findByUserName(userName).orElseThrow(Exception::new);
    }

    @Test
    public void findByDepartmentAndCategory() throws Exception {
        //when
        List<Major> majors = majorQueryRepository.findByDepartmentAndCategory(user.getDepartment(), null, user.getCompletedNumbers());

        //then
        majors.forEach(major -> System.out.println(major.getCategory()));
    }

    @Test
    public void findByDepartmentAndCategoryMajorAdvanced() throws Exception {
        //given
        Category category = MAJOR_ADVANCED;

        //when
        List<Major> majors = majorQueryRepository.findByDepartmentAndCategory(user.getDepartment(), category, user.getCompletedNumbers());

        //then
        majors.forEach(major -> assertEquals(major.getCategory(), category));
    }

    @Test
    public void findByDepartmentAndCategoryMajorOptional() throws Exception {
        //given
        Category category = MAJOR_OPTIONAL;

        //when
        List<Major> majors = majorQueryRepository.findByDepartmentAndCategory(user.getDepartment(), category, user.getCompletedNumbers());

        //then
        majors.forEach(major -> assertEquals(major.getCategory(), category));
    }

    @Test
    public void findByDepartmentAndCategoryEtc() throws Exception {
        //given
        Category category = ETC;

        //when
        List<Major> majors = majorQueryRepository.findByDepartmentAndCategory(user.getDepartment(), category, user.getCompletedNumbers());

        //then
        majors.forEach(major -> assertEquals(major.getCategory(), category));
    }
}
