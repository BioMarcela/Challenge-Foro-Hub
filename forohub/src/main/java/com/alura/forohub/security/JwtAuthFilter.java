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

        // 2. Si el encabezado existe y empieza con "Bearer ", procesamos el token
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);

            if (tokenService.validarToken(token)) {
                // 3. Si el token es válido, extraemos el nombre de usuario
                String username = tokenService.obtenerLogin(token);

                // 4. Creamos la autenticación para que Spring sepa que el usuario es válido
                var authentication = new UsernamePasswordAuthenticationToken(
                        username, null, List.of(new SimpleGrantedAuthority("ROLE_USER")));

                // 5. Guardamos al usuario en el contexto de seguridad
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                // Si el token es inválido, respondemos con 401 y cortamos la petición
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json");
                response.getWriter().write("{\"error\":\"Token inválido o expirado\"}");
                return;
            }
        }

        // 6. ¡Muy importante! Dejamos que la petición siga su curso
        filterChain.doFilter(request, response);
    }
}

