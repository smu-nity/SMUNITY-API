package com.smunity.graduation.domain.course.service;

import com.smunity.graduation.domain.accounts.entity.User;
import com.smunity.graduation.domain.accounts.repository.user.UserRepository;
import com.smunity.graduation.domain.auth.dto.AuthCourseResponseDto;
import com.smunity.graduation.domain.course.dto.CourseResponseDto;
import com.smunity.graduation.domain.course.dto.ResultResponseDto;
import com.smunity.graduation.domain.course.entity.Course;
import com.smunity.graduation.domain.course.repository.course.CourseRepository;
import com.smunity.graduation.global.exception.code.ErrorCode;
import com.smunity.graduation.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CourseService {

    private final UserRepository userRepository;
    private final CourseRepository courseRepository;

    public ResultResponseDto<CourseResponseDto> createCourses(List<AuthCourseResponseDto> requestDtoList, String username) {
        User user = userRepository.findByUserName(username)
                .orElseThrow(() -> new CustomException(ErrorCode._UNAUTHORIZED));
        List<Course> courses = requestDtoList.stream()
                .filter(dto -> !Objects.equals(dto.grade(), "F") &&
                        !courseRepository.existsByUserUserNameAndNumber(username, dto.number())
                )
                .map(dto -> {
                    Course course = dto.toEntity();
                    course.setUser(user);
                    return course;
                })
                .toList();
        courseRepository.saveAll(courses);
        List<CourseResponseDto> responseDtoList = CourseResponseDto.from(user.getCourses());
        return ResultResponseDto.of(user.getYear().getTotal(), user.getCompletedCredits(), responseDtoList);
    }
}
