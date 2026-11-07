package com.idat.soportetecnico.dto;

import com.idat.soportetecnico.model.EstadoSolicitud;
import com.idat.soportetecnico.model.GravedadIncidencia;
import com.idat.soportetecnico.model.Prioridad;

import java.time.LocalDateTime;

public class SolicitudResponse {

    private Long id;
    private String titulo;
    private String descripcion;
    private Prioridad prioridad;
    private EstadoSolicitud estado;

    // Datos del cliente
    private Long clienteId;
    private String clienteNombre;
    private String clienteEmpresa;
    private String clienteArea;
    private String clienteTelefono;

    // Datos del técnico
    private Long tecnicoId;
    private String tecnicoNombre;

    // Clasificación
    private GravedadIncidencia gravedad;

    // Fechas
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaLimite;
    private LocalDateTime fechaInicioAtencion;
    private LocalDateTime fechaResolucion;
    private LocalDateTime fechaCierre;

    public SolicitudResponse() {
    }

    public SolicitudResponse(
            Long id,
            String titulo,
            String descripcion,
            Prioridad prioridad,
            EstadoSolicitud estado,
            Long clienteId,
            String clienteNombre,
            Long tecnicoId,
            String tecnicoNombre
    ) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.prioridad = prioridad;
        this.estado = estado;
        this.clienteId = clienteId;
        this.clienteNombre = clienteNombre;
        this.tecnicoId = tecnicoId;
        this.tecnicoNombre = tecnicoNombre;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Prioridad getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(Prioridad prioridad) {
        this.prioridad = prioridad;
    }

    public EstadoSolicitud getEstado() {
        return estado;
    }

    public void setEstado(EstadoSolicitud estado) {
        this.estado = estado;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public String getClienteNombre() {
        return clienteNombre;
    }

    public void setClienteNombre(String clienteNombre) {
        this.clienteNombre = clienteNombre;
    }

    public String getClienteEmpresa() {
        return clienteEmpresa;
    }

    public void setClienteEmpresa(String clienteEmpresa) {
        this.clienteEmpresa = clienteEmpresa;
    }

    public String getClienteArea() {
        return clienteArea;
    }

    public void setClienteArea(String clienteArea) {
        this.clienteArea = clienteArea;
    }

    public String getClienteTelefono() {
        return clienteTelefono;
    }

    public void setClienteTelefono(String clienteTelefono) {
        this.clienteTelefono = clienteTelefono;
    }

    public Long getTecnicoId() {
        return tecnicoId;
    }

    public void setTecnicoId(Long tecnicoId) {
        this.tecnicoId = tecnicoId;
    }

    public String getTecnicoNombre() {
        return tecnicoNombre;
    }

    public void setTecnicoNombre(String tecnicoNombre) {
        this.tecnicoNombre = tecnicoNombre;
    }

    public GravedadIncidencia getGravedad() {
        return gravedad;
    }

    public void setGravedad(GravedadIncidencia gravedad) {
        this.gravedad = gravedad;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public LocalDateTime getFechaLimite() {
        return fechaLimite;
    }

    public void setFechaLimite(LocalDateTime fechaLimite) {
        this.fechaLimite = fechaLimite;
    }

    public LocalDateTime getFechaInicioAtencion() {
        return fechaInicioAtencion;
    }

    public void setFechaInicioAtencion(LocalDateTime fechaInicioAtencion) {
        this.fechaInicioAtencion = fechaInicioAtencion;
    }

    public LocalDateTime getFechaResolucion() {
        return fechaResolucion;
    }

    public void setFechaResolucion(LocalDateTime fechaResolucion) {
        this.fechaResolucion = fechaResolucion;
    }

    public LocalDateTime getFechaCierre() {
        return fechaCierre;
    }

    public void setFechaCierre(LocalDateTime fechaCierre) {
        this.fechaCierre = fechaCierre;
    }
}