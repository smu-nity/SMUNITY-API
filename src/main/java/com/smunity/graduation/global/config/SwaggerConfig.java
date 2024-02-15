package com.smunity.graduation.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class SwaggerConfig {

	// url: http://localhost:8080/swagger-ui/index.html#/
	@Bean
	public OpenAPI getOpenApi() {
		Server server = new Server().url("/");

		return new OpenAPI()
			.info(getSwaggerInfo())
			.components(authSetting())
			.addServersItem(server)
			.addSecurityItem(new SecurityRequirement().addList("access-token"));
	}

	private Info getSwaggerInfo() {
		License license = new License();
		license.setName("{Application}");

		return new Info()
			.title("Smunity API Document")
			.description("This is Smunity's API document.")
			.version("v0.0.1")
			.license(license);
	}

	private Components authSetting() {
		return new Components()
			.addSecuritySchemes(
				"access-token",
				new SecurityScheme()
					.type(SecurityScheme.Type.HTTP)
					.scheme("bearer")
					.bearerFormat("JWT"));
	}
}
