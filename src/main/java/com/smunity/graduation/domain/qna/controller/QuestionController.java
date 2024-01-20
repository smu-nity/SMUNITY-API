package com.smunity.graduation.domain.qna.controller;

import com.smunity.graduation.domain.qna.dto.QuestionListResponseDto;
import com.smunity.graduation.domain.qna.dto.QuestionRequestDto;
import com.smunity.graduation.domain.qna.dto.QuestionResponseDto;
import com.smunity.graduation.domain.qna.service.QuestionQueryService;
import com.smunity.graduation.domain.qna.service.QuestionService;
import com.smunity.graduation.global.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/questions")
public class QuestionController {
    private final QuestionService questionService;
    private final QuestionQueryService questionQueryService;

//    @PostMapping("/")
//    public ApiResponse<QuestionResponseDto> createQuestion(
//            @RequestBody QuestionRequestDto requestDto) {
//        return ApiResponse.onSuccess(questionService.createQuestion(requestDto));
//    }

    @PutMapping("/{questionId}")
    public ApiResponse<QuestionResponseDto> updateQuestion(
            @PathVariable Long questionId,
            @RequestBody QuestionRequestDto requestDto) {
        return ApiResponse.onSuccess(questionService.updateQuestion(questionId, requestDto));
    }

    @GetMapping("/")
    public ApiResponse<List<QuestionListResponseDto>> getQuestionList() {
        List<QuestionListResponseDto> questions = questionQueryService.getQuestionList();
        return ApiResponse.onSuccess(questions);
    }

    @DeleteMapping("/{questionId}")
    public ApiResponse<Void> deleteQuestion(
            @PathVariable Long questionId) {
        questionService.deleteQuestion(questionId);
        return ApiResponse.noContent();
    }
}
