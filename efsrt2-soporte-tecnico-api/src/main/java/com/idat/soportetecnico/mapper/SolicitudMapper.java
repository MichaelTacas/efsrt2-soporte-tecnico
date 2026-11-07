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

    public SolicitudResponse toResponse(
            Solicitud solicitud
    ) {

        SolicitudResponse response =
                new SolicitudResponse();

        response.setId(
                solicitud.getId()
        );

        response.setTitulo(
                solicitud.getTitulo()
        );

        response.setDescripcion(
                solicitud.getDescripcion()
        );

        response.setPrioridad(
                solicitud.getPrioridad()
        );

        response.setEstado(
                solicitud.getEstado()
        );

        response.setGravedad(
                solicitud.getGravedad()
        );

        // =========================================
        // FECHAS
        // =========================================

        response.setFechaCreacion(
                solicitud.getFechaCreacion()
        );

        response.setFechaLimite(
                solicitud.getFechaLimite()
        );

        response.setFechaInicioAtencion(
                solicitud.getFechaInicioAtencion()
        );

        response.setFechaResolucion(
                solicitud.getFechaResolucion()
        );

        response.setFechaCierre(
                solicitud.getFechaCierre()
        );

        // =========================================
        // CLIENTE
        // =========================================

        if (solicitud.getCliente() != null) {

            Cliente cliente =
                    solicitud.getCliente();

            response.setClienteId(
                    cliente.getId()
            );

            response.setClienteNombre(
                    cliente.getNombre()
            );

            response.setClienteEmpresa(
                    cliente.getEmpresa()
            );

            response.setClienteArea(
                    cliente.getArea()
            );

            response.setClienteTelefono(
                    cliente.getTelefono()
            );
        }

        // =========================================
        // TÉCNICO
        // =========================================

        if (solicitud.getTecnico() != null) {

            Tecnico tecnico =
                    solicitud.getTecnico();

            response.setTecnicoId(
                    tecnico.getId()
            );

            response.setTecnicoNombre(
                    tecnico.getNombre()
            );
        }

        return response;
    }
}