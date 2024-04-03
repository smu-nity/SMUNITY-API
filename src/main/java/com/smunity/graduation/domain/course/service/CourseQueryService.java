package com.smunity.graduation.domain.course.service;

import com.smunity.graduation.domain.accounts.entity.User;
import com.smunity.graduation.domain.accounts.repository.user.UserRepository;
import com.smunity.graduation.domain.course.dto.ResultResponseDto;
import com.smunity.graduation.domain.course.entity.Course;
import com.smunity.graduation.domain.course.entity.Standard;
import com.smunity.graduation.domain.course.repository.StandardRepository;
import com.smunity.graduation.domain.course.repository.course.CourseRepository;
import com.smunity.graduation.global.common.ErrorCode;
import com.smunity.graduation.global.common.enums.Category;
import com.smunity.graduation.global.common.exception.CustomException;
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

    public ResultResponseDto getCourses(String username, Category category) {
        User user = userRepository.findByUserName(username)
                .orElseThrow(() -> new CustomException(ErrorCode._UNAUTHORIZED));
        int total = standardRepository.findByYearAndCategory(user.getYear(), category)
                .map(Standard::getTotal)
                .orElseGet(() -> user.getYear().getTotal());
        List<Course> courses = courseRepository.findByUsernameAndCategory(username, category);
        return ResultResponseDto.of(total, courses);
    }
}
