package com.smunity.graduation.domain.subject.repository.culture;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.smunity.graduation.domain.subject.entity.Culture;
import com.smunity.graduation.global.common.type.SubDomain;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.smunity.graduation.domain.subject.entity.QCulture.culture;

@Repository
@RequiredArgsConstructor
public class CultureQueryRepositoryImpl implements CultureQueryRepository {

    private final JPAQueryFactory query;

    @Override
    public List<Culture> findBySubDomain(SubDomain subDomain) {
        return query.selectFrom(culture)
                .where(subDomainEq(subDomain))
                .fetch();
    }

    private BooleanExpression subDomainEq(SubDomain subDomain) {
        return subDomain != null ? culture.subDomain.eq(subDomain) : null;
    }
}
