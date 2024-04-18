package com.smunity.graduation.domain.accounts.dto;

import com.smunity.graduation.domain.accounts.entity.User;
import lombok.Builder;

@Builder
public record UserInfoResponseDto(
        Long id,
        String email,
        String userName,
        String name,
        String year,
        String department
) {

    public static UserInfoResponseDto from(User user) {
        return UserInfoResponseDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .userName(user.getUserName())
                .name(user.getName())
                .year(user.getYear().getName())
                .department(user.getDepartment().getName())
                .build();
    }
}
