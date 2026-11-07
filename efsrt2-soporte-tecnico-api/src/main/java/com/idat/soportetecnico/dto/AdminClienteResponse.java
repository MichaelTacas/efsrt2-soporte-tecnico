package com.idat.soportetecnico.dto;

import java.time.LocalDateTime;

public class AdminClienteResponse {

    private Long id;
    private String nombre;
    private String email;
    private String empresa;
    private LocalDateTime fechaCreacion;
    private boolean activo;

    public AdminClienteResponse() {
    }

    public AdminClienteResponse(
            Long id,
            String nombre,
            String email,
            String empresa,
            LocalDateTime fechaCreacion,
            boolean activo) {

        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.empresa = empresa;
        this.fechaCreacion = fechaCreacion;
        this.activo = activo;
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getEmail() {
        return email;
    }

    public String getEmpresa() {
        return empresa;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public boolean isActivo() {
        return activo;
    }
}