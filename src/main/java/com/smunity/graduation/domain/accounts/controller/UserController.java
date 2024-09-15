package com.smunity.graduation.domain.accounts.controller;

import com.smunity.graduation.domain.accounts.annotation.AccountResolver;
import com.smunity.graduation.domain.accounts.dto.UserInfoResponseDto;
import com.smunity.graduation.domain.accounts.entity.User;
import com.smunity.graduation.domain.accounts.service.UserQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users/me")
public class UserController {

    private final UserQueryService userQueryService;

    @GetMapping
    public ResponseEntity<UserInfoResponseDto> getUserInfo(@AccountResolver User user) {
        UserInfoResponseDto responseDto = userQueryService.getUserInfo(user.getUserName());
        return ResponseEntity.ok(responseDto);
    }
}
