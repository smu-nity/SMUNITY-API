package com.smunity.graduation.domain.qna.controller;

import com.smunity.graduation.domain.accounts.annotation.AccountResolver;
import com.smunity.graduation.domain.accounts.entity.User;
import com.smunity.graduation.domain.qna.dto.QuestionsResponseDto;
import com.smunity.graduation.domain.qna.dto.QuestionRequestDto;
import com.smunity.graduation.domain.qna.dto.QuestionResponseDto;
import com.smunity.graduation.domain.qna.service.QnAServiceUtils;
import com.smunity.graduation.domain.qna.service.QuestionQueryService;
import com.smunity.graduation.domain.qna.service.QuestionService;
import com.smunity.graduation.global.common.ApiResponse;
import lombok.RequiredArgsConstructor;

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
    private final QnAServiceUtils qnAServiceUtils;

    @PostMapping("/")
    public ApiResponse<QuestionResponseDto> createQuestion(
            @RequestBody QuestionRequestDto requestDto,
            @AccountResolver User user) {
        return ApiResponse.onSuccess(questionService.createQuestion(requestDto, user));
    }

    @PutMapping("/{questionId}")
    public ApiResponse<QuestionResponseDto> updateQuestion(
            @PathVariable Long questionId,
            @RequestBody QuestionRequestDto requestDto,
            @AccountResolver User user) {
        return ApiResponse.onSuccess(questionService.updateQuestion(questionId, requestDto, user));
    }

    @GetMapping("/")
    public ApiResponse<Page<QuestionsResponseDto>> getQuestionList(@PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<QuestionsResponseDto> questions = questionQueryService.getQuestionList(pageable);
        return ApiResponse.onSuccess(questions);
    }

    @DeleteMapping("/{questionId}")
    public ApiResponse<Void> deleteQuestion(
            @PathVariable Long questionId,
            @AccountResolver User user) {
        questionService.deleteQuestion(questionId, user);
        return ApiResponse.noContent();
    }
}
