package com.smunity.graduation.domain.course.service;

import com.smunity.graduation.domain.accounts.entity.User;
import com.smunity.graduation.domain.accounts.repository.user.UserRepository;
import com.smunity.graduation.domain.course.dto.ResultResponseDto;
import com.smunity.graduation.domain.course.entity.Course;
import com.smunity.graduation.domain.course.entity.Curriculum;
import com.smunity.graduation.domain.course.entity.Standard;
import com.smunity.graduation.domain.course.entity.SubDomainHolder;
import com.smunity.graduation.domain.course.repository.CurriculumRepository;
import com.smunity.graduation.domain.course.repository.StandardRepository;
import com.smunity.graduation.domain.course.repository.course.CourseRepository;
import com.smunity.graduation.global.common.ErrorCode;
import com.smunity.graduation.global.common.enums.Category;
import com.smunity.graduation.global.common.enums.Domain;
import com.smunity.graduation.global.common.enums.SubDomain;
import com.smunity.graduation.global.common.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.smunity.graduation.global.common.enums.SubDomain.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CourseQueryService {

    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final StandardRepository standardRepository;
    private final CurriculumRepository curriculumRepository;

    public ResultResponseDto getCourses(String username, Category category) {
        User user = userRepository.findByUserName(username)
                .orElseThrow(() -> new CustomException(ErrorCode._UNAUTHORIZED));
        int total = standardRepository.findByYearAndCategory(user.getYear(), category)
                .map(Standard::getTotal)
                .orElseGet(() -> user.getYear().getTotal());
        List<Course> courses = courseRepository.findByUsernameAndCategory(username, category);
        return ResultResponseDto.of(total, courses);
    }

    public ResultResponseDto getCultureCourses(String username, Domain domain) {
        User user = userRepository.findByUserName(username)
                .orElseThrow(() -> new CustomException(ErrorCode._UNAUTHORIZED));
        List<Curriculum> curriculums = curriculumRepository.findAllByYearAndDomain(user.getYear(), domain);
        List<Course> courses = courseRepository.findAllByUserUserNameAndSubDomainIsNotNull(username);
        int total = getTotal(curriculums.size(), domain);
        SubDomain depSubDomain = user.getDepartment().getSubDomain();
        return ResultResponseDto.of(total, getSubDomains(curriculums, depSubDomain), getSubDomains(courses, depSubDomain));
    }

    private int getTotal(int size, Domain domain) {
        return switch (domain) {
            case CORE -> 2;
            case BALANCE -> 3;
            default -> size;
        };
    }

    private boolean checkNaturalEngineer(SubDomain subDomain) {
        return subDomain.equals(BALANCE_NATURAL) || subDomain.equals(BALANCE_ENGINEER);
    }

    private List<SubDomain> excludedSubDomains(SubDomain subDomain) {
        return checkNaturalEngineer(subDomain) ? List.of(subDomain, BALANCE_NATURAL_ENGINEER) : List.of(subDomain);
    }

    private List<SubDomain> getSubDomains(List<? extends SubDomainHolder> holders, SubDomain depSubDomain) {
        return holders.stream()
                .map(SubDomainHolder::getSubDomain)
                .filter(subDomain -> !excludedSubDomains(depSubDomain).contains(subDomain))
                .toList();
    }
}
