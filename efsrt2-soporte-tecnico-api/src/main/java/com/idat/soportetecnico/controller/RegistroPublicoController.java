package com.idat.soportetecnico.controller;

import com.idat.soportetecnico.dto.AdminClienteResponse;
import com.idat.soportetecnico.dto.RegistroClienteRequest;
import com.idat.soportetecnico.dto.RegistroPublicoClienteRequest;
import com.idat.soportetecnico.service.RegistroClienteService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class RegistroPublicoController {

    private final RegistroClienteService registroClienteService;

    public RegistroPublicoController(
            RegistroClienteService registroClienteService
    ) {
        this.registroClienteService = registroClienteService;
    }

    @PostMapping("/registro")
    public ResponseEntity<AdminClienteResponse> registrar(
            @Valid @RequestBody RegistroPublicoClienteRequest request
    ) {

        RegistroClienteRequest registro =
                new RegistroClienteRequest();

        registro.setNombre(request.getNombre());
        registro.setEmail(request.getEmail());
        registro.setEmpresa(request.getEmpresa());
        registro.setPassword(request.getPassword());

        // El registro público siempre crea
        // una cuenta activa de CLIENTE.
        registro.setActivo(true);

        AdminClienteResponse response =
                registroClienteService.registrarCliente(
                        registro
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }
}