package com.richglez.hotel.auth.controller;

import com.richglez.hotel.auth.dto.AuthResponse;
import com.richglez.hotel.auth.dto.LoginRequest;
import com.richglez.hotel.auth.dto.RegisterRequest;
import com.richglez.hotel.auth.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** Exposes authentication endpoints for registration and login. */
@Tag(name = "Authentication", description = "Authentication Controller")
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    /** Registers a new guest user and returns a JWT. */
    @Operation(summary = "Register", security = @SecurityRequirement(name = ""))
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Usuario registrado exitosamente",
                content = @Content(schema = @Schema(implementation = AuthResponse.class))),
        @ApiResponse(responseCode = "400", description = "Datos invalidos o email ya existe",
                content = @Content)
    })
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    /** Authenticates a user and returns a JWT. */
    @Operation(summary = "Login", security = @SecurityRequirement(name = ""))
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Login exitoso, retorna JWT",
                content = @Content(schema = @Schema(implementation = AuthResponse.class))),
        @ApiResponse(responseCode = "401", description = "Credenciales incorrectas",
                content = @Content)
    })
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }
}
