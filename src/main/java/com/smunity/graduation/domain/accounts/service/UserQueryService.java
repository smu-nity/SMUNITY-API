package com.smunity.graduation.domain.accounts.service;

import com.smunity.graduation.domain.accounts.dto.UserInfoResponseDto;
import com.smunity.graduation.domain.accounts.entity.User;
import com.smunity.graduation.domain.accounts.repository.user.UserRepository;
import com.smunity.graduation.global.common.ErrorCode;
import com.smunity.graduation.global.common.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserQueryService {

    private final UserRepository userRepository;

    public UserInfoResponseDto getUserInfo(String username) {
        User user = userRepository.findByUserName(username)
                .orElseThrow(() -> new CustomException(ErrorCode._UNAUTHORIZED));
        return UserInfoResponseDto.from(user);
    }
}
