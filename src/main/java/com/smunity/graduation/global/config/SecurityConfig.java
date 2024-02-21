package com.smunity.graduation.global.config;

import java.util.Arrays;
import java.util.stream.Stream;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.smunity.graduation.domain.accounts.jwt.exception.JwtAccessDeniedHandler;
import com.smunity.graduation.domain.accounts.jwt.exception.JwtAuthenticationEntryPoint;
import com.smunity.graduation.domain.accounts.jwt.filter.CustomLoginFilter;
import com.smunity.graduation.domain.accounts.jwt.filter.CustomLogoutHandler;
import com.smunity.graduation.domain.accounts.jwt.filter.JwtAuthenticationFilter;
import com.smunity.graduation.domain.accounts.jwt.filter.JwtExceptionFilter;
import com.smunity.graduation.domain.accounts.jwt.util.HttpResponseUtil;
import com.smunity.graduation.domain.accounts.jwt.util.JwtUtil;
import com.smunity.graduation.domain.accounts.jwt.util.RedisUtil;
import com.smunity.graduation.global.config.encoder.Pbkdf2PasswordEncoder;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	private final String[] swaggerUrls = {"/swagger-ui/**", "/v3/**"};
	private final String[] authUrls = {"/", "/api/v1/accounts/register/**", "/api/v1/accounts/login/**",
		"/api/v1/auth"};
	private final String[] allowedUrls = Stream.concat(Arrays.stream(swaggerUrls), Arrays.stream(authUrls))
		.toArray(String[]::new);

	private final AuthenticationConfiguration authenticationConfiguration;

	private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
	private final JwtUtil jwtUtil;
	private final RedisUtil redisUtil;

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new Pbkdf2PasswordEncoder();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		http
			.cors(cors -> cors
				.configurationSource(CorsConfig.apiConfigurationSource()));

		// csrf disable
		http
			.csrf(AbstractHttpConfigurer::disable);

		// form 로그인 방식 disable
		http
			.formLogin(AbstractHttpConfigurer::disable);

		// http basic 인증 방식 disable
		http
			.httpBasic(AbstractHttpConfigurer::disable);

		// 경로별 인가 작업
		http
			.authorizeHttpRequests(auth -> auth
				.requestMatchers(allowedUrls).permitAll()
				.requestMatchers("/**").authenticated()
				.anyRequest().permitAll()
			);

		// Jwt Filter (with login)
		CustomLoginFilter loginFilter = new CustomLoginFilter(
			authenticationManager(authenticationConfiguration), jwtUtil
		);
		loginFilter.setFilterProcessesUrl("/api/v1/accounts/login");

		http
			.addFilterAt(loginFilter, UsernamePasswordAuthenticationFilter.class);

		http
			.addFilterBefore(new JwtAuthenticationFilter(jwtUtil, redisUtil), CustomLoginFilter.class);

		http
			.addFilterBefore(new JwtExceptionFilter(), JwtAuthenticationFilter.class);

		http
			.exceptionHandling(exception -> exception
				.authenticationEntryPoint(jwtAuthenticationEntryPoint)
				.accessDeniedHandler(jwtAccessDeniedHandler)
			);

		// 세션 사용 안함
		http
			.sessionManagement(session -> session
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			);

		// Logout Filter
		http
			.logout(logout -> logout
				.logoutUrl("/api/v1/accounts/logout")
				.addLogoutHandler(new CustomLogoutHandler(redisUtil, jwtUtil))
				.logoutSuccessHandler((request, response, authentication)
					-> HttpResponseUtil.setSuccessResponse(
					response,
					HttpStatus.OK,
					"로그아웃 성공"
				))
			);

		return http.build();
	}
}
