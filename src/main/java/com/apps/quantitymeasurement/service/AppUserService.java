package com.apps.quantitymeasurement.service;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apps.quantitymeasurement.entities.AppUserEntity;
import com.apps.quantitymeasurement.exception.EmailAlreadyExistsException;
import com.apps.quantitymeasurement.repository.AppUserRepository;

@Service
public class AppUserService implements UserDetailsService {

	private final AppUserRepository repository;
	private final PasswordEncoder passwordEncoder;

	public AppUserService(AppUserRepository repository, PasswordEncoder passwordEncoder) {
		this.repository = repository;
		this.passwordEncoder = passwordEncoder;
	}

	// Spring Security calls this — we use email as the principal
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		AppUserEntity user = repository.findByEmail(normalizeEmail(email))
				.orElseThrow(() -> new UsernameNotFoundException("User not found"));
		return new User(user.getEmail(), user.getPasswordHash(), parseAuthorities(user.getRolesCsv()));
	}

	@Transactional
	public AppUserEntity registerLocalUser(String name, String email, String rawPassword) {
		String cleanEmail = normalizeEmail(email);
		if (cleanEmail.isBlank()) throw new IllegalArgumentException("email is required");
		if (rawPassword == null || rawPassword.isBlank()) throw new IllegalArgumentException("password is required");
		if (repository.existsByEmail(cleanEmail)) throw new EmailAlreadyExistsException("email already exists");

		AppUserEntity entity = AppUserEntity.builder()
				.name(name)
				.email(cleanEmail)
				.username(cleanEmail)
				.passwordHash(passwordEncoder.encode(rawPassword))
				.rolesCsv("ROLE_USER")
				.provider("local")
				.build();
		return repository.save(entity);
	}

	public AppUserEntity getByEmail(String email) {
		return repository.findByEmail(normalizeEmail(email))
				.orElseThrow(() -> new UsernameNotFoundException("User not found"));
	}

	private static String normalizeEmail(String email) {
		return email == null ? "" : email.trim().toLowerCase(Locale.ROOT);
	}

	private static List<SimpleGrantedAuthority> parseAuthorities(String rolesCsv) {
		if (rolesCsv == null || rolesCsv.isBlank()) return List.of();
		return Arrays.stream(rolesCsv.split(",")).map(String::trim).filter(s -> !s.isEmpty())
				.map(SimpleGrantedAuthority::new).toList();
	}
}
