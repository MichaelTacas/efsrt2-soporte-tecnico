package com.idat.soportetecnico.validator;

import com.idat.soportetecnico.exception.ReglaNegocioException;
import com.idat.soportetecnico.model.EstadoSolicitud;
import com.idat.soportetecnico.model.Solicitud;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TransicionEstadoValidatorTest {

    private TransicionEstadoValidator validator;

    @BeforeEach
    void setUp() {
        validator = new TransicionEstadoValidator();
    }

    @Test
    void debePermitirPendienteAEnProceso() {
        Solicitud actual = new Solicitud();
        actual.setEstado(EstadoSolicitud.PENDIENTE);

        Solicitud actualizada = new Solicitud();
        actualizada.setEstado(EstadoSolicitud.EN_PROCESO);

        assertDoesNotThrow(() ->
                validator.validarActualizacion(actual, actualizada)
        );
    }

    @Test
    void debePermitirEnProcesoAResuelta() {
        Solicitud actual = new Solicitud();
        actual.setEstado(EstadoSolicitud.EN_PROCESO);

        Solicitud actualizada = new Solicitud();
        actualizada.setEstado(EstadoSolicitud.RESUELTA);

        assertDoesNotThrow(() ->
                validator.validarActualizacion(actual, actualizada)
        );
    }

    @Test
    void debePermitirResueltaACerrada() {
        Solicitud actual = new Solicitud();
        actual.setEstado(EstadoSolicitud.RESUELTA);

        Solicitud actualizada = new Solicitud();
        actualizada.setEstado(EstadoSolicitud.CERRADA);

        assertDoesNotThrow(() ->
                validator.validarActualizacion(actual, actualizada)
        );
    }

    @Test
    void debeRechazarPendienteACerrada() {
        Solicitud actual = new Solicitud();
        actual.setEstado(EstadoSolicitud.PENDIENTE);

        Solicitud actualizada = new Solicitud();
        actualizada.setEstado(EstadoSolicitud.CERRADA);

        assertThrows(
                ReglaNegocioException.class,
                () -> validator.validarActualizacion(actual, actualizada)
        );
    }
}