package com.smunity.graduation.domain.qna.controller;

import com.smunity.graduation.domain.accounts.annotation.AccountResolver;
import com.smunity.graduation.domain.accounts.entity.User;
import com.smunity.graduation.domain.qna.dto.QuestionRequestDto;
import com.smunity.graduation.domain.qna.dto.QuestionResponseDto;
import com.smunity.graduation.domain.qna.service.QuestionQueryService;
import com.smunity.graduation.domain.qna.service.QuestionService;
import com.smunity.graduation.global.common.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/questions")
public class QuestionController {

    private final QuestionService questionService;
    private final QuestionQueryService questionQueryService;

    @GetMapping
    public ApiResponse<Page<QuestionResponseDto>> getQuestionList(
            @ParameterObject
            @PageableDefault(size = 5, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<QuestionResponseDto> questions = questionQueryService.getQuestionList(pageable);
        return ApiResponse.onSuccess(questions);
    }

    @PostMapping
    public ApiResponse<QuestionResponseDto> createQuestion(
            @RequestBody QuestionRequestDto requestDto,
            @AccountResolver User user) {
        return ApiResponse.onSuccess(questionService.createQuestion(requestDto, user));
    }

    @GetMapping("/{questionId}")
    public ApiResponse<QuestionResponseDto> getQuestion(@PathVariable Long questionId) {
        return ApiResponse.onSuccess(questionQueryService.getQuestion(questionId));
    }

    @PutMapping("/{questionId}")
    public ApiResponse<QuestionResponseDto> updateQuestion(
            @PathVariable Long questionId,
            @RequestBody QuestionRequestDto requestDto,
            @AccountResolver User user) {
        return ApiResponse.onSuccess(questionService.updateQuestion(questionId, requestDto, user));
    }

    @DeleteMapping("/{questionId}")
    public ApiResponse<Void> deleteQuestion(
            @PathVariable Long questionId,
            @AccountResolver User user) {
        questionService.deleteQuestion(questionId, user);
        return ApiResponse.noContent();
    }
}
