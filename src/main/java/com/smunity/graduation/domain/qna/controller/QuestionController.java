package com.smunity.graduation.domain.qna.controller;

import com.smunity.graduation.domain.qna.dto.QuestionsResponseDto;
import com.smunity.graduation.domain.qna.dto.QuestionRequestDto;
import com.smunity.graduation.domain.qna.dto.QuestionResponseDto;
import com.smunity.graduation.domain.qna.service.QuestionQueryService;
import com.smunity.graduation.domain.qna.service.QuestionService;
import com.smunity.graduation.global.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/questions")
public class QuestionController {

    private final QuestionService questionService;
    private final QuestionQueryService questionQueryService;

    //ToDo: 프로필 합쳐 지면 사용자 이름을 가져오기
    @PostMapping("/")
    public ApiResponse<QuestionResponseDto> createQuestion(
            @RequestBody QuestionRequestDto requestDto) {
        return ApiResponse.onSuccess(questionService.createQuestion(requestDto));
    }

    @PutMapping("/{questionId}")
    public ApiResponse<QuestionResponseDto> updateQuestion(
            @PathVariable Long questionId,
            @RequestBody QuestionRequestDto requestDto) {
        return ApiResponse.onSuccess(questionService.updateQuestion(questionId, requestDto));
    }

    @GetMapping("/")
    public ApiResponse<Page<QuestionsResponseDto>> getQuestionList(@PageableDefault() Pageable pageable) {
        Page<QuestionsResponseDto> questions = questionQueryService.getQuestionList(pageable);
        return ApiResponse.onSuccess(questions);
    }

    @DeleteMapping("/{questionId}")
    public ApiResponse<Void> deleteQuestion(
            @PathVariable Long questionId) {
        questionService.deleteQuestion(questionId);
        return ApiResponse.noContent();
    }
}
