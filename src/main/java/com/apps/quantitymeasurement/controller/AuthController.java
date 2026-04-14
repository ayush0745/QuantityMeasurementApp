package com.apps.quantitymeasurement.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.apps.quantitymeasurement.request.LoginRequest;
import com.apps.quantitymeasurement.request.RegisterRequest;
import com.apps.quantitymeasurement.model.TokenResponse;
import com.apps.quantitymeasurement.util.JwtService;
import com.apps.quantitymeasurement.entities.AppUserEntity;
import com.apps.quantitymeasurement.service.AppUserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@Validated
@Tag(name = "Authentication", description = "JWT login and health checks")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final AppUserService appUserService;

    public AuthController(AuthenticationManager authenticationManager,
                          JwtService jwtService,
                          AppUserService appUserService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.appUserService = appUserService;
    }

    @PostMapping("/login")
    @Operation(summary = "Login with email and password")
    public ResponseEntity<TokenResponse> login(@Valid @RequestBody LoginRequest body) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(body.getEmail(), body.getPassword())
        );
        String token = jwtService.generateToken(auth.getName(), Map.of("auth_provider", "local"));
        return ResponseEntity.ok(new TokenResponse(token, "Bearer", jwtService.getExpiresInSeconds()));
    }

    @PostMapping("/register")
    @Operation(summary = "Register with name, email and password")
    public ResponseEntity<TokenResponse> register(@Valid @RequestBody RegisterRequest body) {
        AppUserEntity user = appUserService.registerLocalUser(body.getName(), body.getEmail(), body.getPassword());
        String token = jwtService.generateToken(user.getEmail(), Map.of("auth_provider", "local"));
        return ResponseEntity.ok(new TokenResponse(token, "Bearer", jwtService.getExpiresInSeconds()));
    }

    @GetMapping("/health")
    public Map<String, String> health() {
        return Map.of("status", "ok");
    }
}
