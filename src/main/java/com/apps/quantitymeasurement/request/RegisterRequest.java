package com.apps.quantitymeasurement.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Register a new user account")
public class RegisterRequest {

	@NotBlank
	@Size(min = 2, max = 100)
	@Schema(example = "John Doe")
	private String name;

	@NotBlank
	@Email
	@Schema(example = "user@example.com")
	private String email;

	@NotBlank
	@Size(min = 6, max = 100)
	@Schema(example = "myPassword")
	private String password;

	public String getName() { return name; }
	public void setName(String name) { this.name = name; }
	public String getEmail() { return email; }
	public void setEmail(String email) { this.email = email; }
	public String getPassword() { return password; }
	public void setPassword(String password) { this.password = password; }
}
