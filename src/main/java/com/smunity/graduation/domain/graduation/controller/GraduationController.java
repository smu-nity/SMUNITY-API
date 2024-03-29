package com.smunity.graduation.domain.graduation.controller;

import com.smunity.graduation.domain.accounts.annotation.AccountResolver;
import com.smunity.graduation.domain.accounts.entity.User;
import com.smunity.graduation.domain.graduation.dto.GraduationResponseDto;
import com.smunity.graduation.domain.graduation.dto.SubjectResponseDto;
import com.smunity.graduation.domain.graduation.service.GraduationService;
import com.smunity.graduation.global.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequestMapping("/api/v1/graduation")
@RequiredArgsConstructor
@RestController
public class GraduationController {

    private final GraduationService graduationService;

    //졸업요건 검사 조회
    @GetMapping("")
    public ApiResponse<GraduationResponseDto> getGraduationCriteria(@AccountResolver User user) {
        return ApiResponse.onSuccess(graduationService.getGraduationCriteria(user.getUserName()));
    }

    //추천 과목 조회
    @GetMapping("/recommend")
    public ApiResponse<List<SubjectResponseDto>> getRecommendSubjects(@RequestParam("type") String type, @RequestParam("credit") int credit, @AccountResolver User user) {
        return ApiResponse.onSuccess(graduationService.getRecommendSubjects(type, credit, user.getUserName()));
    }

    //Culture 통합용
    @GetMapping("/organize")
    public void organize() {
        graduationService.organizeSubjects();
    }
}
