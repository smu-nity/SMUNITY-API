package com.smunity.graduation.domain.accounts.repository.user;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.smunity.graduation.domain.accounts.entity.User;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class UserRepositoryImpl implements UserRepository {

	private final UserJpaRepository userJpaRepository;

	@Override
	public Optional<User> findByUserName(String username) {
		return userJpaRepository.findByUserName(username);
	}
}
