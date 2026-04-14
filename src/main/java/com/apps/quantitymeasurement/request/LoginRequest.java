package com.apps.quantitymeasurement.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Login credentials")
public class LoginRequest {

	@NotBlank
	@Email
	@Schema(example = "user@example.com")
	private String email;

	@NotBlank
	@Schema(example = "myPassword")
	private String password;

	public String getEmail() { return email; }
	public void setEmail(String email) { this.email = email; }
	public String getPassword() { return password; }
	public void setPassword(String password) { this.password = password; }
}
