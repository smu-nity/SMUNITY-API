package com.smunity.graduation.domain.subject.repository.major;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.smunity.graduation.domain.accounts.entity.Department;
import com.smunity.graduation.domain.subject.entity.Major;
import com.smunity.graduation.global.common.type.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.smunity.graduation.domain.subject.entity.QMajor.major;

@Repository
@RequiredArgsConstructor
public class MajorQueryRepositoryImpl implements MajorQueryRepository {

    private final JPAQueryFactory query;

    @Override
    public List<Major> findByDepartmentAndCategory(Department department, Category category) {
        return query.selectFrom(major)
                .where(
                        departmentEq(department),
                        categoryEq(category)
                )
                .fetch();
    }

    private BooleanExpression departmentEq(Department department) {
        return department != null ? major.department.eq(department) : null;
    }

    private BooleanExpression categoryEq(Category category) {
        return category != null ? major.category.eq(category) : null;
    }
}
