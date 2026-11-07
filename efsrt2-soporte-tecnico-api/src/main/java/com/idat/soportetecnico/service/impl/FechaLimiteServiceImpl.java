package com.idat.soportetecnico.service.impl;

import com.idat.soportetecnico.exception.ReglaNegocioException;
import com.idat.soportetecnico.model.GravedadIncidencia;
import com.idat.soportetecnico.service.FechaLimiteService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class FechaLimiteServiceImpl
        implements FechaLimiteService {

    @Override
    public LocalDateTime calcularFechaLimite(
            LocalDateTime fechaCreacion,
            GravedadIncidencia gravedad) {

        if (fechaCreacion == null) {
            throw new ReglaNegocioException(
                    "La fecha de creación es obligatoria"
            );
        }

        if (gravedad == null) {
            throw new ReglaNegocioException(
                    "La gravedad es obligatoria"
            );
        }

        return switch (gravedad) {

            case LEVE ->
                    fechaCreacion.plusHours(48);

            case MODERADA ->
                    fechaCreacion.plusHours(24);

            case GRAVE ->
                    fechaCreacion.plusHours(12);

            case CRITICA ->
                    fechaCreacion.plusHours(4);
        };
    }
}