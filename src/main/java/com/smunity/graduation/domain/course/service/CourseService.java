package com.smunity.graduation.domain.course.service;

import com.smunity.graduation.domain.accounts.entity.User;
import com.smunity.graduation.domain.accounts.repository.user.UserRepository;
import com.smunity.graduation.domain.auth.dto.AuthCourseResponseDto;
import com.smunity.graduation.domain.course.dto.CourseResponseDto;
import com.smunity.graduation.domain.course.entity.Course;
import com.smunity.graduation.domain.course.repository.CourseRepository;
import com.smunity.graduation.domain.graduation.entity.Subject;
import com.smunity.graduation.domain.graduation.repository.SubjectRepository;
import com.smunity.graduation.global.common.code.status.ErrorCode;
import com.smunity.graduation.global.common.exception.GeneralException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final UserRepository userRepository;
    private final SubjectRepository subjectRepository;
    private final CourseRepository courseRepository;

    public CourseResponseDto createCourse(AuthCourseResponseDto requestDto) {
        // TODO 사용자 인증 적용
        User user = userRepository.findByUserName("201911019")
                .orElseThrow(() -> new GeneralException(ErrorCode._UNAUTHORIZED));
        Subject subject = subjectRepository.findByNumber(requestDto.number())
                .orElseThrow(() -> new GeneralException(ErrorCode.SUBJECT_NOT_FOUND));
        Course course = requestDto.toEntity();
        course.setUser(user);
        course.setSubject(subject);
        Course saved = courseRepository.save(course);
        return CourseResponseDto.from(saved);
    }

    public List<CourseResponseDto> createCourses(List<AuthCourseResponseDto> requestDtoList) {
        return requestDtoList.stream().map(this::createCourse).collect(Collectors.toList());
    }
}
