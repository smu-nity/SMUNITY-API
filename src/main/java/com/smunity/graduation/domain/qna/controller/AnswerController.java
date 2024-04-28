package com.smunity.graduation.domain.qna.controller;

import com.smunity.graduation.domain.accounts.annotation.AccountResolver;
import com.smunity.graduation.domain.accounts.entity.User;
import com.smunity.graduation.domain.qna.dto.AnswerRequestDto;
import com.smunity.graduation.domain.qna.dto.AnswerResponseDto;
import com.smunity.graduation.domain.qna.service.AnswerQueryService;
import com.smunity.graduation.domain.qna.service.AnswerService;
import com.smunity.graduation.global.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/questions/{questionId}/answer")
public class AnswerController {

    private final AnswerService answerService;
    private final AnswerQueryService answerQueryService;

    @PostMapping
    public ApiResponse<AnswerResponseDto> createAnswer(
            @PathVariable Long questionId,
            @RequestBody AnswerRequestDto requestDto,
            @AccountResolver User user) {
        return ApiResponse.onSuccess(answerService.createAnswer(questionId, requestDto, user));
    }

    @GetMapping
    public ApiResponse<AnswerResponseDto> getAnswer(@PathVariable Long questionId) {
        AnswerResponseDto answer = answerQueryService.getAnswer(questionId);
        return ApiResponse.onSuccess(answer);
    }

    @PutMapping
    public ApiResponse<AnswerResponseDto> updateAnswer(
            @PathVariable Long questionId,
            @RequestBody AnswerRequestDto requestDto,
            @AccountResolver User user) {
        return ApiResponse.onSuccess(answerService.updateAnswer(questionId, requestDto, user));
    }

    @DeleteMapping
    public ApiResponse<Void> deleteAnswer(
            @PathVariable Long questionId,
            @AccountResolver User user) {
        answerService.deleteAnswer(questionId, user);
        return ApiResponse.noContent();
    }
}
