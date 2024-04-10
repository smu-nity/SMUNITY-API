package com.smunity.graduation.domain.subject.repository.culture;

import com.smunity.graduation.domain.subject.entity.Culture;
import com.smunity.graduation.global.common.type.SubDomain;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static com.smunity.graduation.global.common.type.SubDomain.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class CultureQueryRepositoryTest {

    @Autowired
    CultureQueryRepository cultureQueryRepository;

    @Test
    public void findBySubDomain() throws Exception {
        //when
        List<Culture> cultures = cultureQueryRepository.findBySubDomain(null);

        //then
        cultures.forEach(culture -> System.out.println(culture.getSubDomain()));
    }

    @Test
    public void findBySubDomainBasicAccident() throws Exception {
        //given
        SubDomain subDomain = BASIC_ACCIDENT;

        //when
        List<Culture> cultures = cultureQueryRepository.findBySubDomain(subDomain);

        //then
        cultures.forEach(culture -> assertEquals(culture.getSubDomain(), subDomain));
    }

    @Test
    public void findBySubDomainCoreProfessional() throws Exception {
        //given
        SubDomain subDomain = CORE_PROFESSIONAL;

        //when
        List<Culture> cultures = cultureQueryRepository.findBySubDomain(subDomain);

        //then
        cultures.forEach(culture -> assertEquals(culture.getSubDomain(), subDomain));
    }

    @Test
    public void findBySubDomainBalanceHumanities() throws Exception {
        //given
        SubDomain subDomain = BALANCE_HUMANITIES;

        //when
        List<Culture> cultures = cultureQueryRepository.findBySubDomain(subDomain);

        //then
        cultures.forEach(culture -> assertEquals(culture.getSubDomain(), subDomain));
    }
}
