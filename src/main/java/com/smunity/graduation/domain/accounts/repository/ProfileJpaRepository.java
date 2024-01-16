package com.smunity.graduation.domain.accounts.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smunity.graduation.domain.accounts.entity.Profile;

public interface ProfileJpaRepository extends JpaRepository<Profile, Long> {

}
