package com.alura.forohub.controller;

import com.alura.forohub.dto.DatosAutenticacionUsuario;
import com.alura.forohub.model.Usuario;
import com.alura.forohub.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping
    @Transactional
    public ResponseEntity registrar(@RequestBody @Valid DatosAutenticacionUsuario datos, UriComponentsBuilder uriBuilder) {
        // Encriptamos la clave antes de guardar
        String claveEncriptada = passwordEncoder.encode(datos.clave());
        Usuario usuario = new Usuario(datos.login(), claveEncriptada);

        repository.save(usuario);

        URI url = uriBuilder.path("/usuarios/{id}").buildAndExpand(usuario.getId()).toUri();
        return ResponseEntity.created(url).body(datos);
    }
}
