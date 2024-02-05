package com.smunity.graduation.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;

// @Configuration
// public class SwaggerConfig {
//
// 	// url : http://localhost:8080/swagger-ui/index.html#/
// 	@Bean
// 	public OpenAPI getOpenApi() {
// 		Server server = new Server().url("/");
//
// 		return new OpenAPI()
// 			.info(getSwaggerInfo())
// 			.components(authSetting())
// 			.addServersItem(server);
// 	}
//
// 	private Info getSwaggerInfo() {
// 		License license = new License();
// 		license.setName("{Application}");
//
// 		return new Info()
// 			.title("Smunity API Document")
// 			.description("Smunity의 API 문서 입니다.")
// 			.version("v0.0.1")
// 			.license(license);
// 	}
//
// 	private Components authSetting() {
// 		return new Components()
// 			.addSecuritySchemes(
// 				"Authorization",
// 				new SecurityScheme()
// 					.name("Authorization")
// 					.type(SecurityScheme.Type.HTTP)
// 					.scheme("bearer")
// 					.bearerFormat("JWT"));
// 	}
// }
//
//

@Configuration
public class SwaggerConfig {

	@Bean
	public OpenAPI SmunityAPI() {
		Info info = new Info()
			.title("Smunity API")
			.description("Smunity API 명세서")
			.version("1.0.0");

		String jwtSchemeName = "JWT TOKEN";
		// API 요청헤더에 인증정보 포함
		SecurityRequirement securityRequirement = new SecurityRequirement().addList(jwtSchemeName);
		// SecuritySchemes 등록
		Components components = new Components()
			.addSecuritySchemes(jwtSchemeName, new SecurityScheme()
				.name(jwtSchemeName)
				.type(SecurityScheme.Type.HTTP) // HTTP 방식
				.scheme("bearer")
				.bearerFormat("JWT"));

		return new OpenAPI()
			.addServersItem(new Server().url("/"))
			.info(info)
			.addSecurityItem(securityRequirement)
			.components(components);
	}
}
