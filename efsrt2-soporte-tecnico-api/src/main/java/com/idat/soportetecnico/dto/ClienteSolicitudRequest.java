package com.idat.soportetecnico.dto;

import com.idat.soportetecnico.model.GravedadIncidencia;
import com.idat.soportetecnico.model.Prioridad;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ClienteSolicitudRequest {

    @NotBlank(message = "El título es obligatorio")
    private String titulo;

    @NotBlank(message = "La descripción es obligatoria")
    private String descripcion;

    @NotNull(message = "La prioridad es obligatoria")
    private Prioridad prioridad;

    @NotNull(message = "La gravedad es obligatoria")
    private GravedadIncidencia gravedad;

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

    public GravedadIncidencia getGravedad() {
        return gravedad;
    }

    public void setGravedad(GravedadIncidencia gravedad) {
        this.gravedad = gravedad;
    }
}