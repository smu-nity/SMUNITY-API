package com.smunity.graduation.domain.qna.controller;

import com.smunity.graduation.domain.qna.dto.AnswerRequestDto;
import com.smunity.graduation.domain.qna.dto.AnswerResponseDto;
import com.smunity.graduation.domain.qna.service.AnswerQueryService;
import com.smunity.graduation.domain.qna.service.AnswerService;
import com.smunity.graduation.global.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/answers")
public class AnswerController {

    private final AnswerService answerService;
    private final AnswerQueryService answerQueryService;

    @PostMapping("/{question_id}")
    public ApiResponse<AnswerResponseDto> createAnswer(
            @PathVariable Long question_id,
            @RequestBody AnswerRequestDto requestDto) {
        return ApiResponse.onSuccess(answerService.createAnswer(question_id, requestDto));
    }

    @PutMapping("/{answerId}")
    public ApiResponse<AnswerResponseDto> updateAnswer(
            @PathVariable Long answerId,
            @RequestBody AnswerRequestDto requestDto) {
        return ApiResponse.onSuccess(answerService.updateAnswer(answerId, requestDto));
    }

    @GetMapping("/{questionId}")
    public ApiResponse<AnswerResponseDto> getAnswerByQuestionId(@PathVariable Long questionId) {
        AnswerResponseDto answer = answerQueryService.getAnswer(questionId);
        return ApiResponse.onSuccess(answer);
    }

    @DeleteMapping("/{answerId}")
    public ApiResponse<Void> deleteAnswer(
            @PathVariable Long answerId) {
        answerService.deleteAnswer(answerId);
        return ApiResponse.noContent();
    }
}
