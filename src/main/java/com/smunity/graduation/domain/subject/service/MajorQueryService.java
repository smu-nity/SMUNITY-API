package com.smunity.graduation.domain.subject.service;

import com.smunity.graduation.domain.accounts.entity.User;
import com.smunity.graduation.domain.accounts.repository.user.UserRepository;
import com.smunity.graduation.domain.subject.dto.MajorResponseDto;
import com.smunity.graduation.domain.subject.dto.ResultResponseDto;
import com.smunity.graduation.domain.subject.entity.Major;
import com.smunity.graduation.domain.subject.repository.major.MajorQueryRepository;
import com.smunity.graduation.global.common.ErrorCode;
import com.smunity.graduation.global.common.exception.CustomException;
import com.smunity.graduation.global.common.type.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MajorQueryService {

    private final UserRepository userRepository;
    private final MajorQueryRepository majorQueryRepository;

    public ResultResponseDto<MajorResponseDto> getMajors(String username, Category category) {
        User user = userRepository.findByUserName(username)
                .orElseThrow(() -> new CustomException(ErrorCode._UNAUTHORIZED));
        List<Major> majors = majorQueryRepository.findByDepartmentAndCategory(user.getDepartment(), category, user.getCompletedNumbers());
        List<MajorResponseDto> responseDtoList = MajorResponseDto.from(majors);
        return ResultResponseDto.from(responseDtoList);
    }
}
