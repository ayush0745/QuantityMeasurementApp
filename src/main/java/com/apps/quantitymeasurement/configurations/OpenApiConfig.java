package com.apps.quantitymeasurement.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class OpenApiConfig {

	@Bean
	public OpenAPI openAPI() {
		return new OpenAPI()
				.info(new Info().title("Quantity Measurement API").version("v1")
						.description("Quantity operations are protected with Bearer JWT. Obtain a token via POST /api/auth/login or OAuth2 Google login."))
				.components(new Components().addSecuritySchemes("bearer-jwt",
						new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")
								.description("JWT from /api/auth/login or Google OAuth2 success response")));
	}
}
