package com.smunity.graduation.domain.accounts.jwt.userdetails;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.smunity.graduation.domain.accounts.entity.User;
import com.smunity.graduation.domain.accounts.jwt.exception.AccountsExceptionHandler;
import com.smunity.graduation.domain.accounts.repository.user.UserRepository;
import com.smunity.graduation.global.common.ErrorCode;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

	private final UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUserName(username)
			.orElseThrow(() -> new AccountsExceptionHandler(ErrorCode.USER_NOT_FOUND));

		log.info("[*] User found : " + user.getUserName());

		return new CustomUserDetails(user);
	}
}
