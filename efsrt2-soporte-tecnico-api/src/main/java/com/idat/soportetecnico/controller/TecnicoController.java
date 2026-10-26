package com.idat.soportetecnico.controller;

import com.idat.soportetecnico.model.Tecnico;
import com.idat.soportetecnico.service.TecnicoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/tecnicos")
public class TecnicoController {

    private final TecnicoService tecnicoService;

    public TecnicoController(TecnicoService tecnicoService) {
        this.tecnicoService = tecnicoService;
    }

    @PostMapping
    public ResponseEntity<Tecnico> registrar(
            @Valid @RequestBody Tecnico tecnico) {

        Tecnico nuevoTecnico = tecnicoService.registrar(tecnico);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(nuevoTecnico);
    }

    @GetMapping
    public ResponseEntity<List<Tecnico>> listar() {

        return ResponseEntity.ok(tecnicoService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tecnico> buscarPorId(@PathVariable Long id) {

        return ResponseEntity.ok(
                tecnicoService.buscarPorId(id)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tecnico> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody Tecnico tecnico) {

        return ResponseEntity.ok(
                tecnicoService.actualizar(id, tecnico)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {

        tecnicoService.eliminar(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/disponibles")
    public ResponseEntity<List<Tecnico>> listarDisponibles() {

        return ResponseEntity.ok(
                tecnicoService.listarDisponibles()
        );
    }
}