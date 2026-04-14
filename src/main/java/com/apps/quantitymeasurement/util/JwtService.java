package com.apps.quantitymeasurement.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Service;

import com.apps.quantitymeasurement.configurations.AppSecurityProperties;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

	private final AppSecurityProperties properties;
	private final SecretKey signingKey;

	public JwtService(AppSecurityProperties properties) {
		this.properties = properties;
		byte[] keyBytes = Decoders.BASE64.decode(properties.getJwt().getSecretBase64());
		this.signingKey = Keys.hmacShaKeyFor(keyBytes);
	}

	public long getExpirationMs() {
		return properties.getJwt().getExpirationMs();
	}

	public long getExpiresInSeconds() {
		return Math.max(1L, properties.getJwt().getExpirationMs() / 1000L);
	}

	public String generateToken(String subject, Map<String, Object> claims) {
		if (subject == null || subject.isBlank()) {
			throw new IllegalArgumentException("JWT subject must not be blank");
		}
		Map<String, Object> safeClaims = claims != null ? new HashMap<>(claims) : new HashMap<>();
		Date issuedAt = new Date();
		Date expiresAt = new Date(issuedAt.getTime() + properties.getJwt().getExpirationMs());
		return Jwts.builder().claims(safeClaims).subject(subject).issuedAt(issuedAt).expiration(expiresAt)
				.signWith(signingKey).compact();
	}

	public String extractSubject(String token) {
		return parseAllClaims(token).getSubject();
	}

	public Claims parseAllClaims(String token) {
		return Jwts.parser().verifyWith(signingKey).build().parseSignedClaims(token).getPayload();
	}
}
