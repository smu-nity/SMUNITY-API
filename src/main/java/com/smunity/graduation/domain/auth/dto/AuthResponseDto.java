package com.smunity.graduation.domain.auth.dto;

import lombok.Builder;
import org.json.JSONObject;

@Builder
public record AuthResponseDto(
        String name,
        String username,
        String department,
        String email,
        int year,
        int semester
) {

    public static AuthResponseDto from(JSONObject response) {
        JSONObject res = response.getJSONArray("dsStdInfoList").getJSONObject(0);
        return AuthResponseDto.builder()
                .name(res.getString("NM_KOR"))
                .username(res.getString("STDNO"))
                .department(getDepartment(res.getString("TMP_DEPT_MJR_NM")))
                .email(res.getString("EMAIL"))
                .year(res.getInt("SHYR"))
                .semester(res.getInt("CMP_SMT"))
                .build();
    }

    public static String getDepartment(String dept) {
        String[] depts = dept.split(" ");
        return depts[depts.length - 1];
    }
}
