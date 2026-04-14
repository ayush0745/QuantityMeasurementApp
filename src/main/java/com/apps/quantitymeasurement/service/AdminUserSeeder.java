package com.apps.quantitymeasurement.service;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.apps.quantitymeasurement.configurations.AppSecurityProperties;
import com.apps.quantitymeasurement.repository.AppUserRepository;

@Component
public class AdminUserSeeder implements CommandLineRunner {

	private final AppUserRepository repository;
	private final AppUserService appUserService;
	private final AppSecurityProperties securityProperties;

	public AdminUserSeeder(AppUserRepository repository, AppUserService appUserService,
			AppSecurityProperties securityProperties) {
		this.repository = repository;
		this.appUserService = appUserService;
		this.securityProperties = securityProperties;
	}

	@Override
	public void run(String... args) {
		String adminEmail = "admin@local.admin";
		String password = securityProperties.getAdmin().getPassword();
		if (!repository.existsByEmail(adminEmail)) {
			appUserService.registerLocalUser("Admin", adminEmail, password);
		}
	}
}
