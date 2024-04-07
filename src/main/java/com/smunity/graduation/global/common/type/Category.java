package com.smunity.graduation.global.common.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Map;

@Getter
@RequiredArgsConstructor
public enum Category {

    MAJOR_ADVANCED("1전심"),
    MAJOR_OPTIONAL("1전선"),
    CULTURE("교양"),
    ETC("기타");

    private final String name;

    public static Category of(String name) {
        Map<String, Category> categoryMap = Map.of("교필", CULTURE, "교선", CULTURE, "1교직", MAJOR_OPTIONAL);
        return categoryMap.getOrDefault(name, findByName(name));
    }

    private static Category findByName(String name) {
        return Arrays.stream(Category.values())
                .filter(category -> name.contains(category.getName()))
                .findFirst()
                .orElse(ETC);
    }
}
