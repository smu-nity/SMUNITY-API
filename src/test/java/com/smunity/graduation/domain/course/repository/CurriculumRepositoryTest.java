package com.smunity.graduation.domain.course.repository;

import com.smunity.graduation.domain.accounts.entity.Year;
import com.smunity.graduation.domain.accounts.repository.YearJpaRepository;
import com.smunity.graduation.domain.course.entity.Curriculum;
import com.smunity.graduation.global.common.type.Domain;
import com.smunity.graduation.global.common.type.SubDomain;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static com.smunity.graduation.global.common.type.Domain.*;

@SpringBootTest
class CurriculumRepositoryTest {

    @Autowired
    YearJpaRepository yearJpaRepository;

    @Autowired
    CurriculumRepository curriculumRepository;

    @Test
    public void findAllByYearAndDomain() throws Exception {
        //given
        Year year = yearJpaRepository.findByYear("2019").orElseThrow(Exception::new);

        //then
        printSubDomain(year, BASIC);
        printSubDomain(year, CORE);
        printSubDomain(year, BALANCE);
    }

    private void printSubDomain(Year year, Domain domain) {
        List<Curriculum> curriculums = curriculumRepository.findAllByYearAndDomain(year, domain);
        System.out.printf("Year: %s\tDomain: %s\n", year.getYear(), domain.getName());
        curriculums.stream()
                .map(Curriculum::getSubDomain)
                .map(SubDomain::getName)
                .forEach(System.out::println);
    }
}
