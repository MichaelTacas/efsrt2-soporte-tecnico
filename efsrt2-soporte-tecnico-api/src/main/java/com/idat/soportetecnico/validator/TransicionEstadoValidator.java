package com.idat.soportetecnico.validator;

import com.idat.soportetecnico.exception.ReglaNegocioException;
import com.idat.soportetecnico.model.EstadoSolicitud;
import com.idat.soportetecnico.model.Solicitud;
import org.springframework.stereotype.Component;

@Component
public class TransicionEstadoValidator implements SolicitudValidator {

    @Override
    public void validarActualizacion(
            Solicitud solicitudActual,
            Solicitud solicitudActualizada
    ) {

        EstadoSolicitud estadoActual = solicitudActual.getEstado();
        EstadoSolicitud nuevoEstado = solicitudActualizada.getEstado();

        if (nuevoEstado == null || nuevoEstado == estadoActual) {
            return;
        }

        boolean transicionPermitida = switch (estadoActual) {

            case PENDIENTE ->
                    nuevoEstado == EstadoSolicitud.EN_PROCESO;

            case EN_PROCESO ->
                    nuevoEstado == EstadoSolicitud.RESUELTA;

            case RESUELTA ->
                    nuevoEstado == EstadoSolicitud.CERRADA;

            case CERRADA -> false;
        };

        if (!transicionPermitida) {
            throw new ReglaNegocioException(
                    "No se puede cambiar el estado de "
                            + estadoActual
                            + " a "
                            + nuevoEstado
            );
        }
    }
}