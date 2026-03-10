package com.alura.forohub.controller;

import com.alura.forohub.dto.DatosRegistroTopico;
import com.alura.forohub.dto.DatosActualizarTopico;
import com.alura.forohub.model.Topico;
import com.alura.forohub.repository.TopicoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    @Autowired
    private TopicoRepository repository;

    @PostMapping
    public ResponseEntity<Topico> registrarTopico(@RequestBody @Valid DatosRegistroTopico datos, UriComponentsBuilder uriBuilder) {
        Topico topico = new Topico(
                null,
                datos.titulo(),
                datos.mensaje(),
                LocalDateTime.now(),
                "ABIERTO",
                datos.autor(),
                datos.curso()
        );

        repository.save(topico);

        // Generamos la URL dinámica para el nuevo recurso y devolvemos 201 Created
        URI url = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
        return ResponseEntity.created(url).body(topico);
    }

    @GetMapping
    public ResponseEntity<List<Topico>> listarTopicos() {
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Topico> detallarTopico(@PathVariable Long id) {
        var optionalTopico = repository.findById(id);

        if (optionalTopico.isPresent()) {
            return ResponseEntity.ok(optionalTopico.get());
        }

        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Topico> actualizarTopico(@PathVariable Long id, @RequestBody @Valid DatosActualizarTopico datos) {
        var optionalTopico = repository.findById(id);

        if (optionalTopico.isPresent()) {
            Topico topico = optionalTopico.get();
            topico.setTitulo(datos.titulo());
            topico.setMensaje(datos.mensaje());
            topico.setAutor(datos.autor());
            topico.setCurso(datos.curso());

            repository.save(topico);
            return ResponseEntity.ok(topico);
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarTopico(@PathVariable Long id) {
        var optionalTopico = repository.findById(id);

        if (optionalTopico.isPresent()) {
            repository.deleteById(id);
            return ResponseEntity.noContent().build(); // Devuelve 204 No Content
        }

        return ResponseEntity.notFound().build();
    }
}