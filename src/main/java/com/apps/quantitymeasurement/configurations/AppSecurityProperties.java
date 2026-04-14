package com.apps.quantitymeasurement.configurations;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "app.security")
public class AppSecurityProperties {

	private Jwt jwt = new Jwt();
	private Admin admin = new Admin();
	private String oauth2SuccessRedirect = "";
	private List<String> corsAllowedOrigins = new ArrayList<>();

	public Jwt getJwt() { return jwt; }
	public void setJwt(Jwt jwt) { this.jwt = jwt; }
	public Admin getAdmin() { return admin; }
	public void setAdmin(Admin admin) { this.admin = admin; }
	public String getOauth2SuccessRedirect() { return oauth2SuccessRedirect; }
	public void setOauth2SuccessRedirect(String oauth2SuccessRedirect) { this.oauth2SuccessRedirect = oauth2SuccessRedirect; }
	public List<String> getCorsAllowedOrigins() { return corsAllowedOrigins; }
	public void setCorsAllowedOrigins(List<String> corsAllowedOrigins) { this.corsAllowedOrigins = corsAllowedOrigins; }

	public static class Jwt {
		private String secretBase64 = "";
		private long expirationMs = 86_400_000L;

		public String getSecretBase64() { return secretBase64; }
		public void setSecretBase64(String secretBase64) { this.secretBase64 = secretBase64; }
		public long getExpirationMs() { return expirationMs; }
		public void setExpirationMs(long expirationMs) { this.expirationMs = expirationMs; }
	}

	public static class Admin {
		private String username = "admin";
		private String password = "change-me";

		public String getUsername() { return username; }
		public void setUsername(String username) { this.username = username; }
		public String getPassword() { return password; }
		public void setPassword(String password) { this.password = password; }
	}
}
