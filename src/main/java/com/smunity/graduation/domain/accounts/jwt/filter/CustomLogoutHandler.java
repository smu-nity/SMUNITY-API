package com.smunity.graduation.domain.accounts.jwt.filter;

import java.util.concurrent.TimeUnit;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import com.smunity.graduation.domain.accounts.jwt.exception.AccountsExceptionHandler;
import com.smunity.graduation.domain.accounts.jwt.exception.TokenErrorCode;
import com.smunity.graduation.domain.accounts.jwt.util.JwtUtil;
import com.smunity.graduation.domain.accounts.jwt.util.RedisUtil;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class CustomLogoutHandler implements LogoutHandler {

	private final RedisUtil redisUtil;
	private final JwtUtil jwtUtil;

	@Override
	public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
		try {
			log.info("[*] Logout Filter");

			String accessToken = jwtUtil.resolveAccessToken(request);

			redisUtil.save(
				accessToken,
				"logout",
				jwtUtil.getExpTime(accessToken),
				TimeUnit.MILLISECONDS
			);

			redisUtil.delete(
				jwtUtil.getUsername(accessToken)
			);
		} catch (ExpiredJwtException e) {
			log.warn("[*] case : accessToken expired");

			throw new AccountsExceptionHandler(TokenErrorCode.TOKEN_EXPIRED);
		}
	}
}
