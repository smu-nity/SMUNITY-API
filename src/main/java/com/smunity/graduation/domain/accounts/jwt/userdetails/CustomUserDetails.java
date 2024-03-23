package com.smunity.graduation.domain.accounts.jwt.userdetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomUserDetails implements UserDetails {

	private final String username;
	private final String password;
	private final Boolean isStaff;

	public CustomUserDetails(String username, String password, Boolean isStaff) {
		this.username = username;
		this.password = password;
		this.isStaff = isStaff;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> authorities = new ArrayList<>();
		if (isStaff) {
			authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
		} else {
			authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
		}
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
