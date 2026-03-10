package com.alura.forohub.controller;

import com.alura.forohub.dto.DatosRegistroTopico;
import com.alura.forohub.model.Topico;
import com.alura.forohub.repository.TopicoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

import com.alura.forohub.dto.DatosActualizarTopico;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    @Autowired
    private TopicoRepository repository;

    @PostMapping
    public void registrarTopico(@RequestBody @Valid DatosRegistroTopico datos) {

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
    }

    @PutMapping("/{id}")
    public Topico actualizarTopico(@PathVariable Long id, @RequestBody @Valid DatosActualizarTopico datos) {

        var optionalTopico = repository.findById(id);

        if (optionalTopico.isPresent()) {

            Topico topico = optionalTopico.get();

            topico.setTitulo(datos.titulo());
            topico.setMensaje(datos.mensaje());
            topico.setAutor(datos.autor());
            topico.setCurso(datos.curso());

            repository.save(topico);

            return topico;
        }

        return null;
    }
    @DeleteMapping("/{id}")
    public void eliminarTopico(@PathVariable Long id) {

        var optionalTopico = repository.findById(id);

        if(optionalTopico.isPresent()){
            repository.deleteById(id);
        }

    }

    @GetMapping
    public List<Topico> listarTopicos() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public Topico detallarTopico(@PathVariable Long id) {
        return repository.findById(id).orElse(null);
    }
}