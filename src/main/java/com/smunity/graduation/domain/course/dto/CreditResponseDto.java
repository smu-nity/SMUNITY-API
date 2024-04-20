package com.smunity.graduation.domain.course.dto;

import com.smunity.graduation.domain.accounts.entity.User;
import lombok.Builder;

import static com.smunity.graduation.domain.course.dto.StatusResponseDto.calculateCompletion;
import static com.smunity.graduation.domain.course.dto.StatusResponseDto.calculateRequired;
import static com.smunity.graduation.global.common.type.Category.*;

@Builder
public record CreditResponseDto(
        String userName,
        String name,
        int total,
        int completed,
        int major,
        int culture,
        int etc,
        int required,
        int completion
) {

    public static CreditResponseDto from(User user) {
        int total = user.getYear().getTotal();
        int completed = user.getCompletedCredits();
        int major = user.getCompletedCredits(MAJOR_ADVANCED) + user.getCompletedCredits(MAJOR_OPTIONAL);
        int culture = user.getCompletedCredits(CULTURE);
        return CreditResponseDto.builder()
                .userName(user.getUserName())
                .name(user.getName())
                .total(total)
                .completed(completed)
                .major(major)
                .culture(culture)
                .etc(completed - major - culture)
                .required(calculateRequired(total, completed))
                .completion(calculateCompletion(total, completed))
                .build();
    }
}
