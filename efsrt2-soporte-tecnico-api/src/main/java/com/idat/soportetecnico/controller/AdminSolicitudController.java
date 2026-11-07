package com.idat.soportetecnico.controller;

import com.idat.soportetecnico.dto.ClasificarSolicitudRequest;
import com.idat.soportetecnico.dto.SolicitudResponse;
import com.idat.soportetecnico.service.ClasificacionSolicitudService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/solicitudes")
public class AdminSolicitudController {

    private final ClasificacionSolicitudService clasificacionSolicitudService;

    public AdminSolicitudController(
            ClasificacionSolicitudService clasificacionSolicitudService
    ) {
        this.clasificacionSolicitudService =
                clasificacionSolicitudService;
    }

    @PatchMapping("/{id}/clasificar")
    public ResponseEntity<SolicitudResponse> clasificarSolicitud(
            @PathVariable Long id,
            @Valid @RequestBody ClasificarSolicitudRequest request
    ) {

        SolicitudResponse response =
                clasificacionSolicitudService
                        .clasificarSolicitud(id, request);

        return ResponseEntity.ok(response);
    }
}