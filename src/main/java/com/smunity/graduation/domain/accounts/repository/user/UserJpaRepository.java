package com.smunity.graduation.domain.accounts.repository.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smunity.graduation.domain.accounts.entity.User;

public interface UserJpaRepository extends JpaRepository<User, Long> {
	
	Optional<User> findByUserName(String username);
}
