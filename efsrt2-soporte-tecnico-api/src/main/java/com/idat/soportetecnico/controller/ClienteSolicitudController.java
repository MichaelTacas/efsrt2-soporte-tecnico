package com.idat.soportetecnico.controller;

import com.idat.soportetecnico.dto.ClienteSolicitudRequest;
import com.idat.soportetecnico.dto.SolicitudResponse;
import com.idat.soportetecnico.service.ClienteSolicitudService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cliente/solicitudes")
public class ClienteSolicitudController {

    private final ClienteSolicitudService clienteSolicitudService;

    public ClienteSolicitudController(
            ClienteSolicitudService clienteSolicitudService) {
        this.clienteSolicitudService = clienteSolicitudService;
    }

    @PostMapping
    public ResponseEntity<SolicitudResponse> crear(
            @Valid @RequestBody ClienteSolicitudRequest request,
            Authentication authentication) {

        SolicitudResponse response =
                clienteSolicitudService.crearSolicitud(
                        authentication.getName(),
                        request
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<List<SolicitudResponse>> listarMisSolicitudes(
            Authentication authentication) {

        return ResponseEntity.ok(
                clienteSolicitudService.listarMisSolicitudes(
                        authentication.getName()
                )
        );
    }
}