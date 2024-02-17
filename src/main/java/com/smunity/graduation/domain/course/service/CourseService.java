package com.smunity.graduation.domain.course.service;

import com.smunity.graduation.domain.accounts.entity.User;
import com.smunity.graduation.domain.accounts.repository.user.UserRepository;
import com.smunity.graduation.domain.auth.dto.AuthCourseResponseDto;
import com.smunity.graduation.domain.course.dto.CourseResponseDto;
import com.smunity.graduation.domain.course.entity.Course;
import com.smunity.graduation.domain.course.repository.CourseRepository;
import com.smunity.graduation.global.common.ErrorCode;
import com.smunity.graduation.global.common.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class CourseService {

    private final UserRepository userRepository;
    private final CourseRepository courseRepository;

    public List<CourseResponseDto> createCourses(List<AuthCourseResponseDto> requestDtoList, String username) {
        return requestDtoList.stream()
                .filter(dto -> !Objects.equals(dto.grade(), "F"))
                .map(dto -> createCourse(dto, username))
                .toList();
    }

    private CourseResponseDto createCourse(AuthCourseResponseDto requestDto, String username) {
        User user = userRepository.findByUserName(username)
                .orElseThrow(() -> new CustomException(ErrorCode._UNAUTHORIZED));
        Optional<Course> exists = courseRepository.findByUserUserNameAndNumber(username, requestDto.number());
        return exists.map(CourseResponseDto::from)
                .orElseGet(() -> {
                    Course course = requestDto.toEntity();
                    course.setUser(user);
                    Course saved = courseRepository.save(course);
                    return CourseResponseDto.from(saved);
                });
    }
}
