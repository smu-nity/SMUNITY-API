package com.smunity.graduation.domain.subject.service;

import com.smunity.graduation.domain.subject.dto.CultureResponseDto;
import com.smunity.graduation.domain.subject.dto.ResultResponseDto;
import com.smunity.graduation.domain.subject.entity.Culture;
import com.smunity.graduation.domain.subject.repository.culture.CultureQueryRepository;
import com.smunity.graduation.global.common.type.SubDomain;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CultureQueryService {

    private final CultureQueryRepository cultureQueryRepository;

    public ResultResponseDto<CultureResponseDto> getCultures(SubDomain subDomain) {
        List<Culture> cultures = cultureQueryRepository.findBySubDomain(subDomain);
        List<CultureResponseDto> responseDtoList = CultureResponseDto.from(cultures);
        return ResultResponseDto.from(responseDtoList);
    }
}
