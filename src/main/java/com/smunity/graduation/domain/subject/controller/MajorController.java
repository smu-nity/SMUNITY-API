package com.smunity.graduation.domain.subject.controller;

import com.smunity.graduation.domain.accounts.annotation.AccountResolver;
import com.smunity.graduation.domain.accounts.entity.User;
import com.smunity.graduation.domain.subject.dto.MajorResponseDto;
import com.smunity.graduation.domain.subject.dto.ResultResponseDto;
import com.smunity.graduation.domain.subject.service.MajorQueryService;
import com.smunity.graduation.global.common.ApiResponse;
import com.smunity.graduation.global.common.type.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/majors")
public class MajorController {

    private final MajorQueryService majorQueryService;

    @GetMapping
    public ApiResponse<ResultResponseDto<MajorResponseDto>> getMajors(@AccountResolver User user, @RequestParam(required = false) Category category) {
        ResultResponseDto<MajorResponseDto> responseDtoList = majorQueryService.getMajors(user.getUserName(), category);
        return ApiResponse.onSuccess(responseDtoList);
    }
}
