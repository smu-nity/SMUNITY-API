package com.smunity.graduation.domain.accounts.repository.user;

import java.util.Optional;

import com.smunity.graduation.domain.accounts.entity.User;

public interface UserRepository {

	Optional<User> findByUserName(String username);
}
