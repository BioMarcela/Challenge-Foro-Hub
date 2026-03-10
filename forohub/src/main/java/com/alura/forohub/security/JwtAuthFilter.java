package com.alura.forohub.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final TokenService tokenService;

    public JwtAuthFilter(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        // 1. Buscamos el encabezado Authorization
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        // 2. Si el encabezado existe y tiene el formato correcto, intentamos validar
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);

            try {
                if (tokenService.validarToken(token)) {
                    // 3. Extraemos el login del token
                    String username = tokenService.obtenerLogin(token);

                    // 4. Creamos la autenticación
                    var authentication = new UsernamePasswordAuthenticationToken(
                            username, null, List.of(new SimpleGrantedAuthority("ROLE_USER")));

                    // 5. Seteamos la autenticación en el contexto
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } catch (Exception e) {
                // Si ocurre un error validando el token (expirado o mal formado),
                // limpiamos el contexto para asegurar que no haya basura,
                // pero NO cortamos la petición aquí.
                SecurityContextHolder.clearContext();
            }
        }

        // 6. ¡ESTA LÍNEA ES LA MÁS IMPORTANTE!
        // Siempre se ejecuta, permitiendo que las rutas públicas (como /auth/login) avancen.
        filterChain.doFilter(request, response);
    }
}

