package com.smunity.graduation.domain.accounts.jwt.filter;

import static com.smunity.graduation.domain.accounts.jwt.util.ResponseUtil.*;
import static org.springframework.http.HttpStatus.*;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.smunity.graduation.domain.accounts.jwt.dto.CachedHttpServletRequest;
import com.smunity.graduation.domain.accounts.jwt.dto.JwtDto;
import com.smunity.graduation.domain.accounts.jwt.exception.CustomExpiredJwtException;
import com.smunity.graduation.domain.accounts.jwt.exception.CustomNoTokenException;
import com.smunity.graduation.domain.accounts.jwt.userdetails.CustomUserDetails;
import com.smunity.graduation.domain.accounts.jwt.util.JwtUtil;
import com.smunity.graduation.domain.accounts.jwt.util.RedisUtil;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

	private final JwtUtil jwtUtil;
	private final RedisUtil redisUtil;

	@Override
	protected void doFilterInternal(
		@NonNull HttpServletRequest request,
		@NonNull HttpServletResponse response,
		@NonNull FilterChain filterChain
	) throws ServletException, IOException {
		log.info("[*] Jwt Filter");

		CachedHttpServletRequest cachedHttpServletRequest = new CachedHttpServletRequest(request);

		try {
			String accessToken = jwtUtil.resolveAccessToken(cachedHttpServletRequest);

			// accessToken 없이 접근할 경우
			if (accessToken == null) {
				filterChain.doFilter(cachedHttpServletRequest, response);

				return;
			}

			// logout 처리된 accessToken
			if (redisUtil.get(accessToken) != null &&
				redisUtil.get(accessToken).equals("logout")) {
				log.info("[*] Logout accessToken");

				filterChain.doFilter(cachedHttpServletRequest, response);

				return;
			}

			log.info("[*] Authorization with Token");

			authenticateAccessToken(accessToken);

			filterChain.doFilter(cachedHttpServletRequest, response);

		} catch (ExpiredJwtException e) {
			log.warn("[*] case : accessToken Expired");

			// accessToken 만료 시 Body에 있는 refreshToken 확인

			String refreshToken = request.getHeader("refreshToken");

			log.info("[*] refreshToken : " + refreshToken);
			try {
				if (jwtUtil.validateRefreshToken(refreshToken)) {

					log.info("[*] case : accessToken Expired && refreshToken in redis");

					// refreshToken 유효 시 재발급
					JwtDto reissueTokens = jwtUtil.reissueToken(refreshToken);

					setSuccessResponse(response, CREATED, reissueTokens);
				}
			} catch (ExpiredJwtException e1) {
				log.info("[*] case : accessToken, refreshToken expired");
				throw new CustomExpiredJwtException();
			} catch (IllegalArgumentException e2) {
				log.info("[*] case : refreshToken expired");
				throw new CustomNoTokenException();
			}
		}
	}

	private void authenticateAccessToken(String accessToken) {
		CustomUserDetails authInfo = (CustomUserDetails)jwtUtil.getAuthInfo(accessToken);

		log.info("[*] Authority Registration");

		// 스프링 시큐리티 인증 토큰 생성
		Authentication authToken = new UsernamePasswordAuthenticationToken(
			authInfo,
			null,
			authInfo.getAuthorities());

		// 세션에 사용자 등록
		SecurityContextHolder.getContext().setAuthentication(authToken);
	}
}
