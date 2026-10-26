package com.idat.soportetecnico.validator;

import com.idat.soportetecnico.exception.ReglaNegocioException;
import com.idat.soportetecnico.model.Solicitud;
import com.idat.soportetecnico.model.Tecnico;
import org.springframework.stereotype.Component;

@Component
public class TecnicoDisponibleValidator implements SolicitudValidator {

    @Override
    public void validarRegistro(Solicitud solicitud) {
        validarTecnico(solicitud.getTecnico());
    }

    @Override
    public void validarActualizacion(
            Solicitud solicitudActual,
            Solicitud solicitudActualizada
    ) {
        validarTecnico(solicitudActualizada.getTecnico());
    }

    private void validarTecnico(Tecnico tecnico) {

        if (tecnico != null && !tecnico.isDisponible()) {
            throw new ReglaNegocioException(
                    "El técnico seleccionado no se encuentra disponible"
            );
        }
    }
}