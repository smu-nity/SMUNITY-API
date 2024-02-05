package com.smunity.graduation.domain.accounts.jwt.filter;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

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
		try {
			String accessToken = jwtUtil.resolveAccessToken(request);

			// accessToken 없이 접근할 경우
			if (accessToken == null) {
				filterChain.doFilter(request, response);
				return;
			}

			// logout 처리된 accessToken
			if (redisUtil.get(accessToken) != null &&
				redisUtil.get(accessToken).equals("logout")) {
				log.info("[*] Logout accessToken");
				filterChain.doFilter(request, response);
				return;
			}

			log.info("[*] Authorization with Token");
			authenticateAccessToken(accessToken);
			filterChain.doFilter(request, response);
		} catch (ExpiredJwtException e) {
			log.warn("[*] case : accessToken Expired");
		}
	}

	private void authenticateAccessToken(String accessToken) {
		CustomUserDetails userDetails = new CustomUserDetails(
			jwtUtil.getUsername(accessToken),
			null,
			jwtUtil.isStaff(accessToken)
		);

		log.info("[*] Authority Registration");

		// 스프링 시큐리티 인증 토큰 생성
		Authentication authToken = new UsernamePasswordAuthenticationToken(
			userDetails,
			null,
			userDetails.getAuthorities());

		// 세션에 사용자 등록
		SecurityContextHolder.getContext().setAuthentication(authToken);
	}
}
