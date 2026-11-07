package com.idat.soportetecnico.controller;

import com.idat.soportetecnico.dto.SolicitudResponse;
import com.idat.soportetecnico.service.TecnicoSolicitudService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tecnico/solicitudes")
public class TecnicoSolicitudController {

    private final TecnicoSolicitudService tecnicoSolicitudService;

    public TecnicoSolicitudController(
            TecnicoSolicitudService tecnicoSolicitudService
    ) {
        this.tecnicoSolicitudService = tecnicoSolicitudService;
    }

    @GetMapping
    public ResponseEntity<List<SolicitudResponse>> listarMisSolicitudes(
            Authentication authentication
    ) {

        return ResponseEntity.ok(
                tecnicoSolicitudService.listarMisSolicitudes(
                        authentication.getName()
                )
        );
    }

    @PatchMapping("/{id}/iniciar")
    public ResponseEntity<SolicitudResponse> iniciarAtencion(
            @PathVariable Long id,
            Authentication authentication
    ) {

        return ResponseEntity.ok(
                tecnicoSolicitudService.iniciarAtencion(
                        authentication.getName(),
                        id
                )
        );
    }

    @PatchMapping("/{id}/resolver")
    public ResponseEntity<SolicitudResponse> resolverSolicitud(
            @PathVariable Long id,
            Authentication authentication
    ) {

        return ResponseEntity.ok(
                tecnicoSolicitudService.resolverSolicitud(
                        authentication.getName(),
                        id
                )
        );
    }
}