package com.smunity.graduation.domain.qna.controller;

import com.smunity.graduation.domain.accounts.annotation.AccountResolver;
import com.smunity.graduation.domain.accounts.entity.User;
import com.smunity.graduation.domain.qna.dto.QuestionRequestDto;
import com.smunity.graduation.domain.qna.dto.QuestionResponseDto;
import com.smunity.graduation.domain.qna.service.QuestionQueryService;
import com.smunity.graduation.domain.qna.service.QuestionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/questions")
public class QuestionController {

    private final QuestionService questionService;
    private final QuestionQueryService questionQueryService;

    @GetMapping
    public ResponseEntity<Page<QuestionResponseDto>> getQuestionList(
            @ParameterObject
            @PageableDefault(size = 5, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<QuestionResponseDto> questions = questionQueryService.getQuestionList(pageable);
        return ResponseEntity.ok(questions);
    }

    @PostMapping
    public ResponseEntity<QuestionResponseDto> createQuestion(
            @RequestBody @Valid QuestionRequestDto requestDto,
            @AccountResolver User user) {
        return ResponseEntity.ok(questionService.createQuestion(requestDto, user));
    }

    @GetMapping("/{questionId}")
    public ResponseEntity<QuestionResponseDto> getQuestion(@PathVariable Long questionId) {
        return ResponseEntity.ok(questionQueryService.getQuestion(questionId));
    }

    @PutMapping("/{questionId}")
    public ResponseEntity<QuestionResponseDto> updateQuestion(
            @PathVariable Long questionId,
            @RequestBody @Valid QuestionRequestDto requestDto,
            @AccountResolver User user) {
        return ResponseEntity.ok(questionService.updateQuestion(questionId, requestDto, user));
    }

    @DeleteMapping("/{questionId}")
    public ResponseEntity<Void> deleteQuestion(
            @PathVariable Long questionId,
            @AccountResolver User user) {
        questionService.deleteQuestion(questionId, user);
        return ResponseEntity.noContent().build();
    }
}
