package com.smunity.graduation.domain.accounts.annotation;

import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.smunity.graduation.domain.accounts.entity.User;
import com.smunity.graduation.domain.accounts.exception.AccountsExceptionHandler;
import com.smunity.graduation.domain.accounts.jwt.util.JwtUtil;
import com.smunity.graduation.domain.accounts.repository.user.UserRepository;
import com.smunity.graduation.global.common.code.status.ErrorCode;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
@Transactional
public class AccountsArgumentResolver implements HandlerMethodArgumentResolver {

	private final UserRepository userRepository;
	private final JwtUtil jwtUtil;

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		boolean hasParameterAnnotation = parameter.hasParameterAnnotation(Accounts.class);
		boolean isOrganizationParameterType = parameter.getParameterType().isAssignableFrom(User.class);

		// TODO 구현

		log.info("========================== supportsParameter 작동 ===================================");
		return hasParameterAnnotation && isOrganizationParameterType;
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
		NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

		log.info("========================== resolveArgument 작동 ===================================");

		String token = webRequest.getHeader("Authorization");
		log.info(token);
		String username = jwtUtil.getUsername(token);

		User user = userRepository.findByUserName(username)
			.orElseThrow(() -> new AccountsExceptionHandler(ErrorCode.USER_NOT_FOUND));
		return user;
	}
}
