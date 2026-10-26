package com.idat.soportetecnico.controller;

import com.idat.soportetecnico.application.SolicitudApplicationService;
import com.idat.soportetecnico.dto.SolicitudRequest;
import com.idat.soportetecnico.dto.SolicitudResponse;
import com.idat.soportetecnico.model.EstadoSolicitud;
import com.idat.soportetecnico.model.Prioridad;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/solicitudes")
public class SolicitudController {

    private final SolicitudApplicationService solicitudApplicationService;

    public SolicitudController(
            SolicitudApplicationService solicitudApplicationService
    ) {
        this.solicitudApplicationService = solicitudApplicationService;
    }

    @PostMapping
    public ResponseEntity<SolicitudResponse> registrar(
            @Valid @RequestBody SolicitudRequest request
    ) {

        SolicitudResponse response =
                solicitudApplicationService.registrar(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<List<SolicitudResponse>> listar() {

        return ResponseEntity.ok(
                solicitudApplicationService.listar()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<SolicitudResponse> buscarPorId(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                solicitudApplicationService.buscarPorId(id)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<SolicitudResponse> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody SolicitudRequest request
    ) {

        return ResponseEntity.ok(
                solicitudApplicationService.actualizar(
                        id,
                        request
                )
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @PathVariable Long id
    ) {

        solicitudApplicationService.eliminar(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<SolicitudResponse>> buscarPorEstado(
            @PathVariable EstadoSolicitud estado
    ) {

        return ResponseEntity.ok(
                solicitudApplicationService.buscarPorEstado(
                        estado
                )
        );
    }

    @GetMapping("/prioridad/{prioridad}")
    public ResponseEntity<List<SolicitudResponse>> buscarPorPrioridad(
            @PathVariable Prioridad prioridad
    ) {

        return ResponseEntity.ok(
                solicitudApplicationService.buscarPorPrioridad(
                        prioridad
                )
        );
    }
}