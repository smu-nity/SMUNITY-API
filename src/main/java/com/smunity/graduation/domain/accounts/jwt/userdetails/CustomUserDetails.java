package com.smunity.graduation.domain.accounts.jwt.userdetails;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.smunity.graduation.domain.accounts.entity.User;

public class CustomUserDetails implements UserDetails {

	private final String username;
	private final String password;
	private final Boolean isStaff;

	public CustomUserDetails(User user) {
		username = user.getUserName();
		password = user.getPassword();
		isStaff = user.getIsStaff();
	}

	public CustomUserDetails(String username, String password, Boolean isStaff) {
		this.username = username;
		this.password = password;
		this.isStaff = isStaff;
	}

	public Boolean getStaff() {
		return isStaff;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
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
