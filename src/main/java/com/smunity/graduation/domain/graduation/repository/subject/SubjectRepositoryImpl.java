package com.smunity.graduation.domain.graduation.repository.subject;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class SubjectRepositoryImpl implements SubjectRepository {

    private final CultureJpaRepository cultureJpaRepository;
    private final MajorJpaRepository majorJpaRepository;
    private final SubjectJpaRepository subjectJpaRepository;

}
