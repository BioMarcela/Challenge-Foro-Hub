package com.alura.forohub.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "usuarios")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Usuario implements UserDetails { // <--- ESTO ES LO QUE TE FALTABA

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String login;
    private String clave;

    public Usuario(String login, String clave) {
        this.login = login;
        this.clave = clave;
    }

    // --- AQUÍ EMPIEZAN LOS MÉTODOS OBLIGATORIOS QUE PIDE SPRING ---

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Le asignamos un rol básico para que pueda pasar
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return clave;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Importante ponerlo en true
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Importante ponerlo en true
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Importante ponerlo en true
    }

    @Override
    public boolean isEnabled() {
        return true; // Importante ponerlo en true
    }
}