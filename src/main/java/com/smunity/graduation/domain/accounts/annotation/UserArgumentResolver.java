package com.smunity.graduation.domain.accounts.annotation;

import org.springframework.core.MethodParameter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.smunity.graduation.domain.accounts.entity.User;
import com.smunity.graduation.domain.accounts.service.AccountsQueryService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
@Transactional
public class UserArgumentResolver implements HandlerMethodArgumentResolver {

	private final AccountsQueryService accountsQueryService;

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

		return accountsQueryService.findByUserName(((UserDetails)userDetails).getUsername());
	}
}
