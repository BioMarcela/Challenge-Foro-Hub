package com.alura.forohub.controller;

import com.alura.forohub.dto.LoginRequest; // 1. Agregamos el import del Record
import com.alura.forohub.security.TokenService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    public AuthController(AuthenticationManager authenticationManager, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        // 2. Quitamos los "get" aquí abajo
        UsernamePasswordAuthenticationToken authInputToken =
                new UsernamePasswordAuthenticationToken(loginRequest.username(), loginRequest.password());

        Authentication authentication = authenticationManager.authenticate(authInputToken);

        String token = tokenService.generarToken(authentication.getName());

        return ResponseEntity.ok(token);
    }
}
