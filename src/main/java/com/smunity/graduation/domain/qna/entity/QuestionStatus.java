package com.smunity.graduation.domain.qna.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum QuestionStatus {
    RECEIPT("접수"),
    COMPLETE("답변완료"),
    ;

    private final String value;

    public static QuestionStatus from(Question question) {
        return question.getAnswer() != null ? COMPLETE : RECEIPT;
    }
}
