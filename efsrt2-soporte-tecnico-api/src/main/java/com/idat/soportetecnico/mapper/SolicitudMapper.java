package com.idat.soportetecnico.mapper;

import com.idat.soportetecnico.dto.SolicitudRequest;
import com.idat.soportetecnico.dto.SolicitudResponse;
import com.idat.soportetecnico.model.Cliente;
import com.idat.soportetecnico.model.Solicitud;
import com.idat.soportetecnico.model.Tecnico;
import org.springframework.stereotype.Component;

@Component
public class SolicitudMapper {

    public Solicitud toEntity(
            SolicitudRequest request,
            Cliente cliente,
            Tecnico tecnico
    ) {

        Solicitud solicitud = new Solicitud();

        solicitud.setTitulo(request.getTitulo());
        solicitud.setDescripcion(request.getDescripcion());
        solicitud.setPrioridad(request.getPrioridad());
        solicitud.setGravedad(request.getGravedad());
        solicitud.setEstado(request.getEstado());
        solicitud.setCliente(cliente);
        solicitud.setTecnico(tecnico);

        return solicitud;
    }

    public SolicitudResponse toResponse(Solicitud solicitud) {

        SolicitudResponse response = new SolicitudResponse();

        response.setId(solicitud.getId());
        response.setTitulo(solicitud.getTitulo());
        response.setDescripcion(solicitud.getDescripcion());
        response.setPrioridad(solicitud.getPrioridad());
        response.setEstado(solicitud.getEstado());
        response.setGravedad(solicitud.getGravedad());

        response.setFechaCreacion(solicitud.getFechaCreacion());
        response.setFechaLimite(solicitud.getFechaLimite());
        response.setFechaInicioAtencion(solicitud.getFechaInicioAtencion());
        response.setFechaResolucion(solicitud.getFechaResolucion());
        response.setFechaCierre(solicitud.getFechaCierre());

        if (solicitud.getCliente() != null) {
            response.setClienteId(
                    solicitud.getCliente().getId()
            );

            response.setClienteNombre(
                    solicitud.getCliente().getNombre()
            );
        }

        if (solicitud.getTecnico() != null) {
            response.setTecnicoId(
                    solicitud.getTecnico().getId()
            );

            response.setTecnicoNombre(
                    solicitud.getTecnico().getNombre()
            );
        }

        return response;
    }
}