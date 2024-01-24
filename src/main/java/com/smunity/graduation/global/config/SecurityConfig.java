package com.smunity.graduation.global.config;

import com.smunity.graduation.domain.accounts.jwt.filter.CustomLoginFilter;
import com.smunity.graduation.domain.accounts.jwt.filter.CustomLogoutHandler;
import com.smunity.graduation.domain.accounts.jwt.filter.JwtExceptionFilter;
import com.smunity.graduation.domain.accounts.jwt.filter.JwtFilter;
import com.smunity.graduation.domain.accounts.jwt.util.JwtUtil;
import com.smunity.graduation.domain.accounts.jwt.util.RedisUtil;
import com.smunity.graduation.global.config.encoder.Pbkdf2PasswordEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;

import java.util.Arrays;
import java.util.stream.Stream;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final String[] swaggerUrls = {"/swagger-ui/**", "/v3/**"};
    private final String[] authUrls = {"/", "/api/v1/accounts/**", "/api/v1/auth", "/graduation/**"};
    private final String[] allowedUrls = Stream.concat(Arrays.stream(swaggerUrls), Arrays.stream(authUrls))
            .toArray(String[]::new);

    private final AuthenticationConfiguration authenticationConfiguration;
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
                .cors((cors) -> cors
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
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers(allowedUrls).permitAll()
                        .requestMatchers("/").permitAll()
                        .anyRequest().authenticated()
                );

        // Jwt Filter (with login)
        CustomLoginFilter loginFilter = new CustomLoginFilter(
                authenticationManager(authenticationConfiguration), jwtUtil
        );
        loginFilter.setFilterProcessesUrl("/api/v1/accounts/login");

        http
                .addFilterAt(loginFilter, UsernamePasswordAuthenticationFilter.class);

        http
                .addFilterBefore(new JwtFilter(jwtUtil, redisUtil), CustomLoginFilter.class);

        // Exception Handle filter
        http
                .addFilterBefore(new JwtExceptionFilter(), LogoutFilter.class);

        // 세션 설정
        http
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );

        // Logout Filter
        http
                .logout((logout) -> logout
                        .logoutUrl("/api/v1/accounts/logout")
                        .logoutSuccessUrl("/")
                        .addLogoutHandler(new CustomLogoutHandler(redisUtil, jwtUtil))
                );

        return http.build();
    }
}
