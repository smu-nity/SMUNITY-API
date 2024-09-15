package com.smunity.graduation.domain.accounts.jwt.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smunity.graduation.domain.accounts.jwt.dto.JwtDto;
import com.smunity.graduation.domain.accounts.jwt.userdetails.CustomUserDetails;
import com.smunity.graduation.domain.accounts.jwt.util.HttpResponseUtil;
import com.smunity.graduation.domain.accounts.jwt.util.JwtUtil;
import com.smunity.graduation.global.common.dto.ErrorResponse;
import com.smunity.graduation.global.exception.code.ErrorCode;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
public class CustomLoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @Override
    public Authentication attemptAuthentication(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response
    ) throws AuthenticationException {
        log.info("[*] Login Filter");

        Map<String, Object> requestBody;
        try {
            requestBody = getBody(request);
        } catch (IOException e) {
            throw new AuthenticationServiceException("Error of request body.");
        }

        log.info("[*] Request Body : " + requestBody);

        String username = (String) requestBody.get("username");
        String password = (String) requestBody.get("password");

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password,
                null);

        return authenticationManager.authenticate(authToken);
    }

    @Override
    protected void successfulAuthentication(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain chain,
            @NonNull Authentication authentication) throws IOException {
        log.info("[*] Login Success");

        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();

        log.info("[*] Login with " + customUserDetails.getUsername());

        JwtDto jwtDto = new JwtDto(
                jwtUtil.createJwtAccessToken(customUserDetails),
                jwtUtil.createJwtRefreshToken(customUserDetails)
        );

        HttpResponseUtil.setSuccessResponse(response, HttpStatus.CREATED, jwtDto);
    }

    @Override
    protected void unsuccessfulAuthentication(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull AuthenticationException failed) throws IOException {

        logger.info("[*] Login Fail");

        ErrorCode errorCode = ErrorCode._UNAUTHORIZED;
        String errorMessage;
        if (failed instanceof BadCredentialsException) {
            errorMessage = "Bad credentials";
        } else if (failed instanceof LockedException) {
            errorMessage = "Account is locked";
        } else if (failed instanceof DisabledException) {
            errorMessage = "Account is disabled";
        } else if (failed instanceof UsernameNotFoundException) {
            errorMessage = "Account not found";
        } else if (failed instanceof AuthenticationServiceException) {
            errorMessage = "Error occurred while parsing request body";
        } else {
            errorMessage = "Authentication failed";
        }

        HttpResponseUtil.setErrorResponse(
                response, errorCode.getHttpStatus(),
                ErrorResponse.of(errorCode.getCode(), errorMessage)
        );
    }

    private Map<String, Object> getBody(HttpServletRequest request) throws IOException {

        StringBuilder stringBuilder = new StringBuilder();
        String line;

        try (BufferedReader bufferedReader = request.getReader()) {
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
        }

        String requestBody = stringBuilder.toString();
        ObjectMapper objectMapper = new ObjectMapper();

        return objectMapper.readValue(requestBody, Map.class);
    }
}

