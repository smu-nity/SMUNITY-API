package com.smunity.graduation.domain.auth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record AuthRequestDto(
        @NotBlank(message = "[ERROR] 학번 입력은 필수 입니다.")
        String username,
        @NotBlank(message = "[ERROR] 비밀번호 입력은 필수 입니다.")
        String password
) {
        
}
