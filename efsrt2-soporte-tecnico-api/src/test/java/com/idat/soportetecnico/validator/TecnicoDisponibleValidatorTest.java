package com.idat.soportetecnico.validator;

import com.idat.soportetecnico.exception.ReglaNegocioException;
import com.idat.soportetecnico.model.Solicitud;
import com.idat.soportetecnico.model.Tecnico;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TecnicoDisponibleValidatorTest {

    private TecnicoDisponibleValidator validator;

    @BeforeEach
    void setUp() {
        validator = new TecnicoDisponibleValidator();
    }

    @Test
    void debePermitirRegistrarSolicitudConTecnicoDisponible() {

        Tecnico tecnico = new Tecnico();
        tecnico.setDisponible(true);

        Solicitud solicitud = new Solicitud();
        solicitud.setTecnico(tecnico);

        assertDoesNotThrow(() ->
                validator.validarRegistro(solicitud)
        );
    }

    @Test
    void debeRechazarRegistrarSolicitudConTecnicoNoDisponible() {

        Tecnico tecnico = new Tecnico();
        tecnico.setDisponible(false);

        Solicitud solicitud = new Solicitud();
        solicitud.setTecnico(tecnico);

        assertThrows(
                ReglaNegocioException.class,
                () -> validator.validarRegistro(solicitud)
        );
    }

    @Test
    void debePermitirRegistrarSolicitudSinTecnico() {

        Solicitud solicitud = new Solicitud();
        solicitud.setTecnico(null);

        assertDoesNotThrow(() ->
                validator.validarRegistro(solicitud)
        );
    }
}