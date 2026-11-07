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

        RegistroClienteRequest registroRequest =
                new RegistroClienteRequest();

        registroRequest.setNombre(request.getNombre());
        registroRequest.setEmail(request.getEmail());
        registroRequest.setEmpresa(request.getEmpresa());
        registroRequest.setArea(request.getArea());
        registroRequest.setTelefono(request.getTelefono());
        registroRequest.setPassword(request.getPassword());
        registroRequest.setActivo(true);

        AdminClienteResponse response =
                registroClienteService.registrarCliente(
                        registroRequest
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }
}