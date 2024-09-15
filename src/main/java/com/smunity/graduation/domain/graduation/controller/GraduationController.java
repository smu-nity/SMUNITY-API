package com.smunity.graduation.domain.graduation.controller;

import com.smunity.graduation.domain.accounts.annotation.AccountResolver;
import com.smunity.graduation.domain.accounts.entity.User;
import com.smunity.graduation.domain.graduation.dto.GraduationResponseDto;
import com.smunity.graduation.domain.graduation.dto.SubjectResponseDto;
import com.smunity.graduation.domain.graduation.service.GraduationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
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
    @GetMapping
    public ResponseEntity<GraduationResponseDto> getGraduationCriteria(@AccountResolver User user) {
        return ResponseEntity.ok(graduationService.getGraduationCriteria(user.getUserName()));
    }

    //추천 과목 조회
    @GetMapping("/recommend")
    public ResponseEntity<List<SubjectResponseDto>> getRecommendSubjects(@RequestParam("type") String type, @RequestParam("credit") int credit, @AccountResolver User user) {
        return ResponseEntity.ok(graduationService.getRecommendSubjects(type, credit, user.getUserName()));
    }

    //Culture 통합용
    @GetMapping("/organize")
    public void organize() {
        graduationService.organizeSubjects();
    }
}
