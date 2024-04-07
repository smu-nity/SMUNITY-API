package com.smunity.graduation.domain.graduation.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.smunity.graduation.domain.graduation.entity.Subject;

public interface SubjectRepository extends JpaRepository<Subject, Long> {

	// @Query(value = "select * from graduations_subject where ((type= \"교선\" or type = \"교필\") and domain IS NULL) ORDER BY count DESC", nativeQuery = true)
	// List<Subject> findAllCulturesByCredit(int credit);

	@Query("SELECT s FROM Subject s WHERE (s.type = '교선' OR s.type = '교필') AND s.domain IS NULL ORDER BY s.count DESC")
	List<Subject> findAllCulturesByCredit(int credit);

	// @Query(value = "select * from graduations_subject where domain=:domain and sub_domain=:subDomain ORDER BY count DESC", nativeQuery = true)
	// List<Subject> findCulturesByDomainAndSubDomain(String domain, String subDomain);

	@Query("SELECT s FROM Subject s WHERE s.domain = :domain AND s.subDomain = :subDomain ORDER BY s.count DESC")
	List<Subject> findCulturesByDomainAndSubDomain(String domain, String subDomain);

	// @Query(value = "select * from graduations_subject where domain=:domain ORDER BY count DESC", nativeQuery = true)
	// List<Subject> findCulturesByDomain(String domain);
	// List<Subject> findSubjectByType(String type);

	// @Query(value = "select * from graduations_subject where type = :type and dept = :dept ORDER BY count DESC", nativeQuery = true)
	// List<Subject> findMajorByTypeAndDept(String type, String dept);

	@Query("SELECT s FROM Subject s WHERE s.type = :type AND s.dept = :dept ORDER BY s.count DESC")
	List<Subject> findMajorByTypeAndDept(String type, String dept);
}
