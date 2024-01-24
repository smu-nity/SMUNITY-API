package com.smunity.graduation.domain.graduation.controller;

import com.smunity.graduation.domain.graduation.dto.GraduationResponseDto;
import com.smunity.graduation.domain.graduation.service.GraduationService;
import com.smunity.graduation.global.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("/graduation")
@RequiredArgsConstructor
@RestController
public class GraduationController {

    private final GraduationService graduationService;

    //졸업요건 검사 조회
    @GetMapping("")
    public ApiResponse<GraduationResponseDto> getGraduationCriteria() {
        return ApiResponse.onSuccess(graduationService.getGraduationCriteria());
    }

    //추천 과목 조회
    @GetMapping("/recommend")
    public ApiResponse<?> getRecommendSubjects(@PathVariable("type") String type) {
        return ApiResponse.onSuccess(graduationService.getGraduationCriteria());
    }

    //Culture 통합용
    @GetMapping("/organize")
    public void organize() {
        graduationService.organizeSubjects();
    }
}
