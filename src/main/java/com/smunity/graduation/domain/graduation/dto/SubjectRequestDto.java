package com.smunity.graduation.domain.graduation.dto;

import java.util.HashMap;


public class SubjectRequestDto {
    HashMap<String, String> request;


    public SubjectRequestDto() {
        request = new HashMap<>();
        request.put("_AUTH_MENU_KEY", "");
        request.put("@d1#strCampusRcd", "CMN001.0001");
        request.put("@d1#strIqyDiv", "02");
        request.put("@d1#strCultDiv", "02");
        request.put("@d1#strCultMandCd", "");
        request.put("@d1#strCultChoiceCd", "");
        request.put("@d1#strUnivCd", "00246");
        request.put("@d1#strDeptCd", "03005");
        request.put("@d1#strShyr", "UCR009.0005");
        request.put("@d1#strReTlsnSchYear", "");
        request.put("@d1#strReTlsnSmtRcd", "");
        request.put("@d1#strReTlsnSesRcd", "");
        request.put("@d1#strReTlsnSbjNo", "");
        request.put("@d1#strTlsnAplyYn", "N");
        request.put("@d1#strCultAraRcd", "");
        request.put("@d#", "@d1#");
        request.put("@d1#", "dmParam");
        request.put("@d1#tp", "dm");
    }

}
