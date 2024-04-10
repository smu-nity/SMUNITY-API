package com.smunity.graduation.domain.subject.controller;

import com.smunity.graduation.domain.subject.dto.CultureResponseDto;
import com.smunity.graduation.domain.subject.dto.ResultResponseDto;
import com.smunity.graduation.domain.subject.service.CultureQueryService;
import com.smunity.graduation.global.common.ApiResponse;
import com.smunity.graduation.global.common.type.SubDomain;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/cultures")
public class CultureController {

    private final CultureQueryService cultureQueryService;

    @GetMapping
    public ApiResponse<ResultResponseDto<CultureResponseDto>> getCultures(@RequestParam(required = false) SubDomain subDomain) {
        ResultResponseDto<CultureResponseDto> responseDto = cultureQueryService.getCultures(subDomain);
        return ApiResponse.onSuccess(responseDto);
    }
}
