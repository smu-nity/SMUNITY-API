package com.smunity.graduation.domain.subject.repository.culture;

import com.smunity.graduation.domain.subject.entity.Culture;
import com.smunity.graduation.global.common.type.SubDomain;

import java.util.List;

public interface CultureQueryRepository {

    List<Culture> findBySubDomain(SubDomain subDomain);
}
