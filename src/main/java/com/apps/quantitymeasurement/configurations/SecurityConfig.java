package com.apps.quantitymeasurement.configurations;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.apps.quantitymeasurement.filters.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource(AppSecurityProperties properties) {
		CorsConfiguration config = new CorsConfiguration();
		List<String> origins = properties.getCorsAllowedOrigins();
		if (origins == null || origins.isEmpty()) {
			config.setAllowedOriginPatterns(List.of("*"));
			config.setAllowCredentials(false);
		} else {
			config.setAllowedOrigins(origins);
			config.setAllowCredentials(true);
		}
		config.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
		config.setAllowedHeaders(List.of("*"));
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config);
		return source;
	}

	@Bean
	@Order(1)
	public SecurityFilterChain apiSecurityFilterChain(HttpSecurity http, JwtAuthenticationFilter jwtAuthenticationFilter,
			Http401UnauthorizedEntryPoint unauthorizedEntryPoint, CorsConfigurationSource corsConfigurationSource)
			throws Exception {
		http.cors(c -> c.configurationSource(corsConfigurationSource)).csrf(AbstractHttpConfigurer::disable)
				.headers(h -> h.frameOptions(f -> f.disable()))
				.sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(auth -> auth
					.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
					.requestMatchers("/api/auth/**").permitAll()
					.requestMatchers(PathRequest.toH2Console()).permitAll()
					.requestMatchers("/error").permitAll()
					.requestMatchers("/v3/api-docs/**", "/swagger-ui/**").permitAll()
					.requestMatchers("/webjars/**").permitAll()
					.anyRequest().authenticated()
				)
				.httpBasic(AbstractHttpConfigurer::disable).formLogin(AbstractHttpConfigurer::disable)
				.exceptionHandling(e -> e.authenticationEntryPoint(unauthorizedEntryPoint))
				.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}
}
