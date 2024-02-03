package com.smunity.graduation.domain.accounts.annotation;

import org.springframework.core.MethodParameter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.smunity.graduation.domain.accounts.entity.User;
import com.smunity.graduation.domain.accounts.exception.AccountsExceptionHandler;
import com.smunity.graduation.domain.accounts.jwt.userdetails.CustomUserDetails;
import com.smunity.graduation.domain.accounts.repository.user.UserRepository;
import com.smunity.graduation.global.common.code.status.ErrorCode;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
@Transactional
public class UserArgumentResolver implements HandlerMethodArgumentResolver {

	private final UserRepository userRepository;

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		boolean hasParameterAnnotation = parameter.hasParameterAnnotation(UserResolver.class);
		boolean isOrganizationParameterType = parameter.getParameterType().isAssignableFrom(User.class);
		return hasParameterAnnotation && isOrganizationParameterType;
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
		NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

		Object userDetails = SecurityContextHolder.getContext()
			.getAuthentication()
			.getPrincipal();

		return userRepository.findByUserName(((CustomUserDetails)userDetails).getUsername())
			.orElseThrow(() -> new AccountsExceptionHandler(ErrorCode.USER_NOT_FOUND));
	}
}
