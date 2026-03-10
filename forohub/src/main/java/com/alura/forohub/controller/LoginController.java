package com.alura.forohub.controller;

import com.alura.forohub.dto.LoginRequest; // 1. Verifica este import
import com.alura.forohub.security.TokenService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Collections;

@RestController
@RequestMapping("/login")
public class LoginController {

    private final TokenService tokenService;

    public LoginController(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @PostMapping
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {

        // 2. Cambiamos getUsername() por username()
        if ("admin".equals(request.username()) && "123456".equals(request.password())) {

            // 3. También aquí cambiamos getUsername() por username()
            String token = tokenService.generarToken(request.username());

            return ResponseEntity.ok(Collections.singletonMap("token", token));
        } else {
            return ResponseEntity.status(401).body(Collections.singletonMap("error", "Usuario o contraseña incorrectos"));
        }
    }
}