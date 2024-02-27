package com.smunity.graduation.domain.course.service;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smunity.graduation.domain.accounts.entity.User;
import com.smunity.graduation.domain.accounts.repository.user.UserRepository;
import com.smunity.graduation.domain.auth.dto.AuthCourseResponseDto;
import com.smunity.graduation.domain.course.dto.CourseResponseDto;
import com.smunity.graduation.domain.course.entity.Course;
import com.smunity.graduation.domain.course.repository.CourseRepository;
import com.smunity.graduation.domain.graduation.entity.Subject;
import com.smunity.graduation.domain.graduation.repository.SubjectRepository;
import com.smunity.graduation.global.common.ErrorCode;
import com.smunity.graduation.global.common.exception.CustomException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CourseService {

	private final UserRepository userRepository;
	private final CourseRepository courseRepository;
	private final SubjectRepository subjectRepository;

	public List<CourseResponseDto> createCourses(List<AuthCourseResponseDto> requestDtoList, String username) {
		User user = userRepository.findByUserName(username)
			.orElseThrow(() -> new CustomException(ErrorCode._UNAUTHORIZED));
		List<Course> courses = requestDtoList.stream()
			.filter(dto -> !Objects.equals(dto.grade(), "F") &&
				!courseRepository.existsByUserUserNameAndNumber(username, dto.number())
			)
			.map(dto -> {
				Course course = dto.toEntity();
				course.setUser(user);

				Subject subject = subjectRepository.findByNumber(course.getNumber())
					.orElseThrow(() -> new CustomException(ErrorCode.SUBJECT_NOT_FOUND));

				subject.updateCount();

				return course;
			})
			.toList();
		courseRepository.saveAll(courses);
		return CourseResponseDto.from(user.getCourses());
	}
}
