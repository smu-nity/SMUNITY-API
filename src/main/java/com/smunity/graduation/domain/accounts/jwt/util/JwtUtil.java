package com.smunity.graduation.domain.accounts.jwt.util;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.smunity.graduation.domain.accounts.jwt.dto.JwtDto;
import com.smunity.graduation.domain.accounts.jwt.exception.SecurityCustomException;
import com.smunity.graduation.domain.accounts.jwt.exception.TokenErrorCode;
import com.smunity.graduation.domain.accounts.jwt.userdetails.CustomUserDetails;
import com.smunity.graduation.domain.accounts.jwt.userdetails.CustomUserDetailsService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtUtil {

	private final static String USERNAME = "username";
	private final static String IS_STAFF = "is_staff";
	private final CustomUserDetailsService customUserDetailsService;
	private final SecretKey secretKey;
	private final Long accessExpMs;
	private final Long refreshExpMs;
	private final RedisUtil redisUtil;

	public JwtUtil(
		CustomUserDetailsService customUserDetailsService, @Value("${jwt.secret}") String secret,
		@Value("${jwt.token.access-expiration-time}") Long access,
		@Value("${jwt.token.refresh-expiration-time}") Long refresh,
		RedisUtil redis) {
		this.customUserDetailsService = customUserDetailsService;

		secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8),
			Jwts.SIG.HS256.key().build().getAlgorithm());
		accessExpMs = access;
		refreshExpMs = refresh;
		redisUtil = redis;
	}

	public String getUsername(String token) throws SignatureException {
		return Jwts.parser()
			.setSigningKey(secretKey)
			.build()
			.parseClaimsJws(token)
			.getBody()
			.get(USERNAME, String.class);
	}

	public boolean isStaff(String token) throws SignatureException {
		return (Boolean)Jwts.parser().setSigningKey(secretKey).build().parseClaimsJws(token).getBody().get(IS_STAFF);
	}

	public boolean isExpired(String token) throws SignatureException {
		return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().getExpiration()
			.before(new Date());
	}

	public long getExpTime(String token) {
		return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().getExpiration()
			.getTime();
	}

	public String createJwtAccessToken(CustomUserDetails customUserDetails) {
		Instant issuedAt = Instant.now();
		Instant expiration = issuedAt.plusMillis(accessExpMs);

		return Jwts.builder()
			.header()
			.add("alg", "HS256")
			.add("typ", "JWT")
			.and()
			.claim(USERNAME, customUserDetails.getUsername())
			.claim(IS_STAFF, customUserDetails.getAuthorities())
			.issuedAt(Date.from(issuedAt))
			.expiration(Date.from(expiration))
			.signWith(secretKey)
			.compact();
	}

	public String createJwtRefreshToken(CustomUserDetails customUserDetails) {
		Instant issuedAt = Instant.now();
		Instant expiration = issuedAt.plusMillis(refreshExpMs);

		String refreshToken = Jwts.builder()
			.header()
			.add("alg", "HS256")
			.add("typ", "JWT")
			.and()
			.claim(USERNAME, customUserDetails.getUsername())
			.claim(IS_STAFF, customUserDetails.getAuthorities())
			.issuedAt(Date.from(issuedAt))
			.expiration(Date.from(expiration))
			.signWith(secretKey)
			.compact();

		redisUtil.save(
			customUserDetails.getUsername(),
			refreshToken,
			refreshExpMs,
			TimeUnit.MILLISECONDS
		);
		return refreshToken;
	}

	public JwtDto reissueToken(String refreshToken) throws SignatureException {
		UserDetails userDetails = customUserDetailsService.loadUserByUsername(getUsername(refreshToken));

		return new JwtDto(
			createJwtAccessToken((CustomUserDetails)userDetails),
			createJwtRefreshToken((CustomUserDetails)userDetails)
		);
	}

	public String resolveAccessToken(HttpServletRequest request) {
		String authorization = request.getHeader("Authorization");

		if (authorization == null || !authorization.startsWith("Bearer ")) {

			log.warn("[*] No Token in req");

			return null;
		}

		log.info("[*] Token exists");

		return authorization.split(" ")[1];
	}

	public boolean validateRefreshToken(String refreshToken) {

		// refreshToken validate
		String username = getUsername(refreshToken);

		//redis 확인
		if (!redisUtil.hasKey(username) || !isExpired(refreshToken)) {
			throw new SecurityCustomException(TokenErrorCode.INVALID_TOKEN);
		}
		return true;
	}

}
