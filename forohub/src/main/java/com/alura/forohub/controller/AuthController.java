package com.alura.forohub.controller;

import com.alura.forohub.dto.LoginRequest;
import com.alura.forohub.security.TokenService;
import com.alura.forohub.model.Usuario; // Asegúrate de que este import sea el de tu modelo
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
        // 1. Empaquetamos las credenciales que vienen de Postman
        UsernamePasswordAuthenticationToken authInputToken =
                new UsernamePasswordAuthenticationToken(loginRequest.username(), loginRequest.password());

        // 2. El AuthenticationManager valida contra la base de datos (aquí ocurre la magia)
        Authentication authentication = authenticationManager.authenticate(authInputToken);

        // 3. Obtenemos el objeto Usuario ya autenticado
        var usuarioAutenticado = (Usuario) authentication.getPrincipal();

        // 4. Generamos el token usando el login del usuario
        String token = tokenService.generarToken(usuarioAutenticado.getLogin());

        // 5. Devolvemos el token con un 200 OK
        return ResponseEntity.ok(token);
    }
}