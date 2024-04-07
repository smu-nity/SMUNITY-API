package com.smunity.graduation.domain.subject.repository.major;

import com.smunity.graduation.domain.subject.entity.Major;
import com.smunity.graduation.global.common.type.Category;

import java.util.List;

public interface MajorQueryRepository {

    List<Major> findByCategory(Category category);
}
