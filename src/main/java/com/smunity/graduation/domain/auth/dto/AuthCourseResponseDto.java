package com.smunity.graduation.domain.auth.dto;

import com.smunity.graduation.domain.course.entity.Course;
import lombok.Builder;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Builder
public record AuthCourseResponseDto(
        String number,
        String name,
        String type,
        String grade,
        String year,
        String semester,
        String domain,
        int credit
) {

    public static List<AuthCourseResponseDto> from(JSONArray objs) {
        List<AuthCourseResponseDto> responses = new ArrayList<>();
        for (int i = 0; i < objs.length(); i++)
            responses.add(from(objs.getJSONObject(i)));
        return responses;
    }

    public static AuthCourseResponseDto from(JSONObject obj) {
        return AuthCourseResponseDto.builder()
                .number(obj.getString("SBJ_NO"))
                .name(obj.getString("SBJ_NM"))
                .type(obj.getString("CMP_DIV_NM"))
                .grade(obj.getString("GRD_NM"))
                .year(obj.getString("SCH_YEAR"))
                .semester(obj.getString("SMT_NM"))
                .domain(getDomain(obj.getString("CULT_ARA_NM")))
                .credit(obj.getInt("CDT"))
                .build();
    }

    public static String getDomain(String domain) {
        return !Objects.equals(domain, "*") ? domain : null;
    }

    public Course toEntity() {
        return Course.builder()
                .name(name)
                .number(number)
                .year(year)
                .semester(semester)
                .type(type)
                .domain(domain)
                .credit(credit)
                .build();
    }
}
