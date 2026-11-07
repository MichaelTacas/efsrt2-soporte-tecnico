package com.idat.soportetecnico.dto;

import com.idat.soportetecnico.model.GravedadIncidencia;
import jakarta.validation.constraints.NotNull;

public class ClasificarSolicitudRequest {

    @NotNull(message = "La gravedad es obligatoria")
    private GravedadIncidencia gravedad;

    @NotNull(message = "El técnico es obligatorio")
    private Long tecnicoId;

    public GravedadIncidencia getGravedad() {
        return gravedad;
    }

    public void setGravedad(GravedadIncidencia gravedad) {
        this.gravedad = gravedad;
    }

    public Long getTecnicoId() {
        return tecnicoId;
    }

    public void setTecnicoId(Long tecnicoId) {
        this.tecnicoId = tecnicoId;
    }
}