package com.smunity.graduation.domain.auth.dto;

public record AuthResponseDto(
        String name,
        String username,
        String department,
        String email,
        int year,
        int semester
) {

}
