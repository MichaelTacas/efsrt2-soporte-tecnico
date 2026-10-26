package com.idat.soportetecnico.validator;

import com.idat.soportetecnico.model.EstadoSolicitud;
import com.idat.soportetecnico.model.Solicitud;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PermisoCambioEstadoValidatorTest {

    private final PermisoCambioEstadoValidator validator =
            new PermisoCambioEstadoValidator();

    @AfterEach
    void limpiarContexto() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void tecnicoNoDebeCerrarSolicitudResuelta() {

        autenticarComo("ROLE_TECNICO");

        Solicitud actual =
                crearSolicitud(EstadoSolicitud.RESUELTA);

        Solicitud actualizada =
                crearSolicitud(EstadoSolicitud.CERRADA);

        assertThrows(
                AccessDeniedException.class,
                () -> validator.validarActualizacion(
                        actual,
                        actualizada
                )
        );
    }

    @Test
    void administradorPuedeCerrarSolicitudResuelta() {

        autenticarComo("ROLE_ADMINISTRADOR");

        Solicitud actual =
                crearSolicitud(EstadoSolicitud.RESUELTA);

        Solicitud actualizada =
                crearSolicitud(EstadoSolicitud.CERRADA);

        assertDoesNotThrow(
                () -> validator.validarActualizacion(
                        actual,
                        actualizada
                )
        );
    }

    @Test
    void tecnicoPuedeMarcarSolicitudComoResuelta() {

        autenticarComo("ROLE_TECNICO");

        Solicitud actual =
                crearSolicitud(EstadoSolicitud.EN_PROCESO);

        Solicitud actualizada =
                crearSolicitud(EstadoSolicitud.RESUELTA);

        assertDoesNotThrow(
                () -> validator.validarActualizacion(
                        actual,
                        actualizada
                )
        );
    }

    private Solicitud crearSolicitud(
            EstadoSolicitud estado
    ) {

        Solicitud solicitud = new Solicitud();
        solicitud.setEstado(estado);

        return solicitud;
    }

    private void autenticarComo(
            String rol
    ) {

        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(
                        "usuario-prueba",
                        null,
                        List.of(
                                new SimpleGrantedAuthority(rol)
                        )
                );

        SecurityContextHolder
                .getContext()
                .setAuthentication(authentication);
    }
}