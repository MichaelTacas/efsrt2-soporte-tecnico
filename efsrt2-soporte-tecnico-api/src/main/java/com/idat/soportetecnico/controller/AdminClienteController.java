package com.idat.soportetecnico.controller;

import com.idat.soportetecnico.dto.ActualizarClienteRequest;
import com.idat.soportetecnico.dto.AdminClienteResponse;
import com.idat.soportetecnico.dto.EstadoUsuarioRequest;
import com.idat.soportetecnico.dto.RegistroClienteRequest;
import com.idat.soportetecnico.dto.RestablecerPasswordRequest;
import com.idat.soportetecnico.service.RegistroClienteService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/clientes")
public class AdminClienteController {

    private final RegistroClienteService registroClienteService;

    public AdminClienteController(
            RegistroClienteService registroClienteService) {

        this.registroClienteService = registroClienteService;
    }

    // Registrar cliente + cuenta de acceso
    @PostMapping
    public ResponseEntity<AdminClienteResponse> registrarCliente(
            @Valid @RequestBody RegistroClienteRequest request) {

        AdminClienteResponse cliente =
                registroClienteService.registrarCliente(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(cliente);
    }

    // Listar clientes
    @GetMapping
    public ResponseEntity<List<AdminClienteResponse>> listarClientes() {

        return ResponseEntity.ok(
                registroClienteService.listarClientes()
        );
    }

    // Editar cliente
    @PutMapping("/{id}")
    public ResponseEntity<AdminClienteResponse> actualizarCliente(
            @PathVariable Long id,
            @Valid @RequestBody ActualizarClienteRequest request) {

        return ResponseEntity.ok(
                registroClienteService.actualizarCliente(
                        id,
                        request
                )
        );
    }

    // Activar o desactivar acceso
    @PatchMapping("/{id}/estado")
    public ResponseEntity<Void> cambiarEstado(
            @PathVariable Long id,
            @RequestBody EstadoUsuarioRequest request) {

        registroClienteService.cambiarEstado(
                id,
                request.isActivo()
        );

        return ResponseEntity.noContent().build();
    }

    // Restablecer contraseña
    @PatchMapping("/{id}/password")
    public ResponseEntity<Void> restablecerPassword(
            @PathVariable Long id,
            @Valid @RequestBody RestablecerPasswordRequest request) {

        registroClienteService.restablecerPassword(
                id,
                request.getPassword()
        );

        return ResponseEntity.noContent().build();
    }
}