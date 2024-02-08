package com.smunity.graduation.domain.graduation.repository;

import com.smunity.graduation.domain.graduation.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {

    @Query(value = "select * from graduations_subject where ((type= \"교선\" or type = \"교필\") and domain IS NULL) ORDER BY count DESC", nativeQuery = true)
    List<Subject> findAllCulturesByCredit(int credit);

    @Query(value = "select * from graduations_subject where domain=:domain and sub_domain=:subDomain ", nativeQuery = true)
    List<Subject> findCulturesByDomainAndSubDomain(String domain, String subDomain);

    @Query(value = "select * from graduations_subject where domain=:domain ", nativeQuery = true)
    List<Subject> findCulturesByDomain(String domain);

    List<Subject> findSubjectByType(String type);

    @Query(value = "select * from graduations_subject where type = :type and dept = :dept", nativeQuery = true)
    List<Subject> findMajorByTypeAndDept(String type, String dept);

    Optional<Subject> findByNumber(String number);
}
