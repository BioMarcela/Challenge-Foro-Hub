package com.alura.forohub.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "usuarios")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String login;

    private String clave;

    // AGREGA ESTO: Constructor para el registro inicial
    public Usuario(String login, String clave) {
        this.login = login;
        this.clave = clave;
    }
}