package com.idat.soportetecnico.validator;

import com.idat.soportetecnico.model.EstadoSolicitud;
import com.idat.soportetecnico.model.Solicitud;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class PermisoCambioEstadoValidator
        implements SolicitudValidator {

    @Override
    public void validarRegistro(Solicitud solicitud) {
        // No aplica al registro.
    }

    @Override
    public void validarActualizacion(
            Solicitud solicitudActual,
            Solicitud solicitudActualizada
    ) {

        boolean intentaCerrar =
                solicitudActual.getEstado() == EstadoSolicitud.RESUELTA
                        &&
                        solicitudActualizada.getEstado() == EstadoSolicitud.CERRADA;

        if (!intentaCerrar) {
            return;
        }

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        boolean esAdministrador =
                authentication != null
                        &&
                        authentication.getAuthorities()
                                .stream()
                                .anyMatch(authority ->
                                        authority.getAuthority()
                                                .equals("ROLE_ADMINISTRADOR")
                                );

        if (!esAdministrador) {
            throw new AccessDeniedException(
                    "Solo un administrador puede cerrar una solicitud"
            );
        }
    }
}