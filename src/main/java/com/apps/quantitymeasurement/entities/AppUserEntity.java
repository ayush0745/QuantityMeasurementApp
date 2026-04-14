package com.apps.quantitymeasurement.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "app_users")
public class AppUserEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(length = 100)
	private String name;

	@Column(nullable = false, unique = true, length = 200)
	private String email;

	@Column(nullable = false, unique = true, length = 100)
	private String username;

	@Column(nullable = false)
	private String passwordHash;

	@Column(nullable = false, length = 100)
	private String rolesCsv;

	@Column(nullable = false, length = 30)
	private String provider;

	public AppUserEntity() {}

	public static Builder builder() { return new Builder(); }

	public static class Builder {
		private Long id;
		private String name;
		private String email;
		private String username;
		private String passwordHash;
		private String rolesCsv;
		private String provider;

		public Builder id(Long id) { this.id = id; return this; }
		public Builder name(String name) { this.name = name; return this; }
		public Builder email(String email) { this.email = email; return this; }
		public Builder username(String username) { this.username = username; return this; }
		public Builder passwordHash(String passwordHash) { this.passwordHash = passwordHash; return this; }
		public Builder rolesCsv(String rolesCsv) { this.rolesCsv = rolesCsv; return this; }
		public Builder provider(String provider) { this.provider = provider; return this; }
		public AppUserEntity build() {
			AppUserEntity e = new AppUserEntity();
			e.id = this.id; e.name = this.name; e.email = this.email;
			e.username = this.username; e.passwordHash = this.passwordHash;
			e.rolesCsv = this.rolesCsv; e.provider = this.provider;
			return e;
		}
	}

	public Long getId() { return id; }
	public void setId(Long id) { this.id = id; }
	public String getName() { return name; }
	public void setName(String name) { this.name = name; }
	public String getEmail() { return email; }
	public void setEmail(String email) { this.email = email; }
	public String getUsername() { return username; }
	public void setUsername(String username) { this.username = username; }
	public String getPasswordHash() { return passwordHash; }
	public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }
	public String getRolesCsv() { return rolesCsv; }
	public void setRolesCsv(String rolesCsv) { this.rolesCsv = rolesCsv; }
	public String getProvider() { return provider; }
	public void setProvider(String provider) { this.provider = provider; }
}
