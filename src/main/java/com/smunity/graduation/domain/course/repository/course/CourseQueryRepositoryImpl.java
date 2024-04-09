package com.smunity.graduation.domain.course.repository.course;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.smunity.graduation.domain.course.entity.Course;
import com.smunity.graduation.global.common.type.Category;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.smunity.graduation.domain.course.entity.QCourse.course;
import static org.springframework.util.StringUtils.hasText;

@RequiredArgsConstructor
public class CourseQueryRepositoryImpl implements CourseQueryRepository {

    private final JPAQueryFactory query;

    @Override
    public List<Course> findByUsernameAndCategory(String userName, Category category) {
        return query.selectFrom(course)
                .where(
                        userNameEq(userName),
                        categoryEq(category)
                )
                .fetch();
    }

    private BooleanExpression userNameEq(String userName) {
        return hasText(userName) ? course.user.userName.eq(userName) : null;
    }

    private BooleanExpression categoryEq(Category category) {
        return category != null ? course.category.eq(category) : null;
    }
}
