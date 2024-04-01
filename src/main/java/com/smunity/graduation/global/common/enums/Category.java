package com.smunity.graduation.global.common.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

@Getter
@RequiredArgsConstructor
public enum Category {

    MAJOR_ADVANCED("1전심"),
    MAJOR_OPTIONAL("1전선"),
    CULTURE_REQUIRED("교필"),
    CULTURE_OPTIONAL("교선"),
    ETC("기타");

    private static final Map<String, Category> lookup = new HashMap<>();

    static {
        for (Category pc : EnumSet.allOf(Category.class)) {
            lookup.put(pc.getName(), pc);
        }
    }

    private final String name;

    public static Category of(String name) {
        if (!lookup.containsKey(name)) {
            return ETC;
        }
        return lookup.get(name);
    }
}
