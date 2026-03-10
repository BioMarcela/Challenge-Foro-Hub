package com.alura.forohub.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration; // en milisegundos

    // Generar token JWT
    public String generarToken(String login) {
        Date ahora = new Date();
        Date exp = new Date(ahora.getTime() + expiration);

        return JWT.create()
                .withSubject(login)
                .withIssuedAt(ahora)
                .withExpiresAt(exp)
                .sign(Algorithm.HMAC256(secret));
    }

    // Validar token JWT
    public boolean validarToken(String token) {
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret)).build();
            verifier.verify(token);
            return true;
        } catch (JWTVerificationException e) {
            return false;
        }
    }

    // Obtener login del token
    public String obtenerLogin(String token) {
        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256(secret)).build().verify(token);
        return decodedJWT.getSubject();
    }
}
