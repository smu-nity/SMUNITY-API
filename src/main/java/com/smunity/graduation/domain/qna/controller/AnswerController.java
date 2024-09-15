package com.smunity.graduation.domain.qna.controller;

import com.smunity.graduation.domain.accounts.annotation.AccountResolver;
import com.smunity.graduation.domain.accounts.entity.User;
import com.smunity.graduation.domain.qna.dto.AnswerRequestDto;
import com.smunity.graduation.domain.qna.dto.AnswerResponseDto;
import com.smunity.graduation.domain.qna.service.AnswerQueryService;
import com.smunity.graduation.domain.qna.service.AnswerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/questions/{questionId}/answer")
public class AnswerController {

    private final AnswerService answerService;
    private final AnswerQueryService answerQueryService;

    @PostMapping
    public ResponseEntity<AnswerResponseDto> createAnswer(
            @PathVariable Long questionId,
            @RequestBody AnswerRequestDto requestDto,
            @AccountResolver User user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(answerService.createAnswer(questionId, requestDto, user));
    }

    @GetMapping
    public ResponseEntity<AnswerResponseDto> getAnswer(@PathVariable Long questionId) {
        AnswerResponseDto answer = answerQueryService.getAnswer(questionId);
        return ResponseEntity.ok(answer);
    }

    @PutMapping
    public ResponseEntity<AnswerResponseDto> updateAnswer(
            @PathVariable Long questionId,
            @RequestBody AnswerRequestDto requestDto,
            @AccountResolver User user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(answerService.updateAnswer(questionId, requestDto, user));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAnswer(
            @PathVariable Long questionId,
            @AccountResolver User user) {
        answerService.deleteAnswer(questionId, user);
        return ResponseEntity.noContent().build();
    }
}
