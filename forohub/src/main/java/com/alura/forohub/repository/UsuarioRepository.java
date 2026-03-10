package com.alura.forohub.repository;

import com.alura.forohub.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    // IMPORTANTE: Debe retornar UserDetails para que el Service no falle
    Optional<UserDetails> findByLogin(String login);
}
