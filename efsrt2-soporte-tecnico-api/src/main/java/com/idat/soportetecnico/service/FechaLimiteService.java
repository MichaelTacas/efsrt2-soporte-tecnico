package com.idat.soportetecnico.service;

import com.idat.soportetecnico.model.GravedadIncidencia;

import java.time.LocalDateTime;

public interface FechaLimiteService {

    LocalDateTime calcularFechaLimite(
            LocalDateTime fechaCreacion,
            GravedadIncidencia gravedad
    );
}