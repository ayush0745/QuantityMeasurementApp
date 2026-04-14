package com.apps.quantitymeasurement.model;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "OAuth2 / JWT access token response")
public record TokenResponse(
		@Schema(example = "eyJhbGciOiJIUzI1NiJ9...") String accessToken,
		@Schema(example = "Bearer") String tokenType,
		@Schema(example = "86400") long expiresInSeconds) {
}
