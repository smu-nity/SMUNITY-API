package com.smunity.graduation.domain.course.service;

import com.smunity.graduation.domain.accounts.entity.User;
import com.smunity.graduation.domain.accounts.entity.Year;
import com.smunity.graduation.domain.accounts.repository.user.UserRepository;
import com.smunity.graduation.domain.course.dto.CourseResponseDto;
import com.smunity.graduation.domain.course.dto.CreditResponseDto;
import com.smunity.graduation.domain.course.dto.CultureResponseDto;
import com.smunity.graduation.domain.course.dto.ResultResponseDto;
import com.smunity.graduation.domain.course.entity.Course;
import com.smunity.graduation.domain.course.entity.Curriculum;
import com.smunity.graduation.domain.course.entity.Standard;
import com.smunity.graduation.domain.course.repository.CurriculumRepository;
import com.smunity.graduation.domain.course.repository.StandardRepository;
import com.smunity.graduation.domain.course.repository.course.CourseRepository;
import com.smunity.graduation.global.exception.code.ErrorCode;
import com.smunity.graduation.global.exception.CustomException;
import com.smunity.graduation.global.common.type.Category;
import com.smunity.graduation.global.common.type.Domain;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CourseQueryService {

    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final StandardRepository standardRepository;
    private final CurriculumRepository curriculumRepository;

    public ResultResponseDto<CourseResponseDto> getCourses(String username, Category category) {
        User user = userRepository.findByUserName(username)
                .orElseThrow(() -> new CustomException(ErrorCode._UNAUTHORIZED));
        List<Course> courses = courseRepository.findByUsernameAndCategory(username, category);
        List<CourseResponseDto> responseDtoList = CourseResponseDto.from(courses);
        int total = getTotal(user.getYear(), category);
        int completed = calculateCompleted(courses);
        return ResultResponseDto.of(total, completed, responseDtoList);
    }

    public ResultResponseDto<CultureResponseDto> getCultureCourses(String username, Domain domain) {
        User user = userRepository.findByUserName(username)
                .orElseThrow(() -> new CustomException(ErrorCode._UNAUTHORIZED));
        List<Curriculum> curriculums = curriculumRepository.findAllByYearAndDomain(user.getYear(), domain);
        List<CultureResponseDto> responseDtoList = CultureResponseDto.of(curriculums, user);
        int total = getCultureTotal(curriculums.size(), domain);
        int completed = calculateCultureCompleted(responseDtoList);
        return ResultResponseDto.of(total, completed, responseDtoList);
    }

    public CreditResponseDto getCoursesCredit(String username) {
        User user = userRepository.findByUserName(username)
                .orElseThrow(() -> new CustomException(ErrorCode._UNAUTHORIZED));
        return CreditResponseDto.from(user);
    }

    private int getTotal(Year year, Category category) {
        return standardRepository.findByYearAndCategory(year, category)
                .map(Standard::getTotal)
                .orElseGet(year::getTotal);
    }

    private int getCultureTotal(int size, Domain domain) {
        return switch (domain) {
            case CORE -> 2;
            case BALANCE -> 3;
            default -> size;
        };
    }

    private int calculateCompleted(List<Course> courses) {
        return courses.stream()
                .mapToInt(Course::getCredit)
                .sum();
    }

    private int calculateCultureCompleted(List<CultureResponseDto> cultures) {
        return cultures.stream()
                .filter(CultureResponseDto::completed)
                .toList()
                .size();
    }
}
