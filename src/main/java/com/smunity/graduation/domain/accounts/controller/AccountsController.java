package com.smunity.graduation.domain.accounts.controller;

import com.smunity.graduation.domain.accounts.annotation.AccountResolver;
import com.smunity.graduation.domain.accounts.dto.UserRegisterRequestDto;
import com.smunity.graduation.domain.accounts.dto.UserRegisterResponseDto;
import com.smunity.graduation.domain.accounts.entity.User;
import com.smunity.graduation.domain.accounts.jwt.dto.JwtDto;
import com.smunity.graduation.domain.accounts.jwt.exception.SecurityCustomException;
import com.smunity.graduation.domain.accounts.jwt.exception.TokenErrorCode;
import com.smunity.graduation.domain.accounts.jwt.util.JwtUtil;
import com.smunity.graduation.domain.accounts.service.AccountsService;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/accounts")
@RestController
public class AccountsController {

    private final AccountsService accountsService;

    private final JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<UserRegisterResponseDto> register(@Valid @RequestBody UserRegisterRequestDto request) {
        UserRegisterResponseDto responseDto = accountsService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @GetMapping("/reissue")
    public ResponseEntity<JwtDto> reissueToken(@RequestHeader("RefreshToken") String refreshToken) {
        try {
            jwtUtil.validateRefreshToken(refreshToken);
            return ResponseEntity.ok(
                    jwtUtil.reissueToken(refreshToken)
            );
        } catch (ExpiredJwtException eje) {
            throw new SecurityCustomException(TokenErrorCode.TOKEN_EXPIRED, eje);
        } catch (IllegalArgumentException iae) {
            throw new SecurityCustomException(TokenErrorCode.INVALID_TOKEN, iae);
        }
    }

    @GetMapping("/test")
    public ResponseEntity<String> register(@AccountResolver User user) {
        return ResponseEntity.ok(user.getUserName());
    }
}
