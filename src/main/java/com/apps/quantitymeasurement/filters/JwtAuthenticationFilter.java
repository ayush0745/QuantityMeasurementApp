package com.apps.quantitymeasurement.filters;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.apps.quantitymeasurement.service.AppUserService;
import com.apps.quantitymeasurement.util.JwtService;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
	private static final String BEARER_PREFIX = "Bearer ";

	private final JwtService jwtService;
	private final AppUserService appUserService;

	public JwtAuthenticationFilter(JwtService jwtService, AppUserService appUserService) {
		this.jwtService = jwtService;
		this.appUserService = appUserService;
	}

	@Override
	protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
			@NonNull FilterChain filterChain) throws ServletException, IOException {
		String path = request.getServletPath();
		if (isDocumentationOrAuthPath(path)) {
			filterChain.doFilter(request, response);
			return;
		}
		String header = request.getHeader("Authorization");
		if (header == null || !header.startsWith(BEARER_PREFIX)) {
			filterChain.doFilter(request, response);
			return;
		}
		String token = header.substring(BEARER_PREFIX.length()).trim();
		if (token.isEmpty()) {
			filterChain.doFilter(request, response);
			return;
		}
		try {
			String subject = jwtService.extractSubject(token);
			if (subject != null && SecurityContextHolder.getContext().getAuthentication() == null) {
				UserDetails userDetails = appUserService.loadUserByUsername(subject);
				UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails.getUsername(),
						null, userDetails.getAuthorities());
				auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(auth);
			}
		} catch (JwtException | IllegalArgumentException | UsernameNotFoundException ex) {
			logger.debug("Invalid JWT: {}", ex.getMessage());
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			response.setContentType("application/json");
			response.getWriter().write("{\"error\":\"invalid_token\"}");
			return;
		}
		filterChain.doFilter(request, response);
	}

	private static boolean isDocumentationOrAuthPath(String path) {
	    return path.startsWith("/api/auth/login")
	        || path.startsWith("/api/auth/register")
	        || path.startsWith("/api/auth/health")
	        || path.startsWith("/v3/api-docs")
	        || path.startsWith("/swagger-ui")
	        || path.startsWith("/webjars/")
	        || path.startsWith("/h2-console")
	        || path.startsWith("/oauth2/")
	        || path.startsWith("/login/oauth2/");
	}

}
