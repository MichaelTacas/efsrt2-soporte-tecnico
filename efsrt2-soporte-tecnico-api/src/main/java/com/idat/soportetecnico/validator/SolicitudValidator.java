package com.idat.soportetecnico.validator;

import com.idat.soportetecnico.model.Solicitud;

public interface SolicitudValidator {

    default void validarRegistro(Solicitud solicitud) {
    }

    default void validarActualizacion(
            Solicitud solicitudActual,
            Solicitud solicitudActualizada
    ) {
    }
}