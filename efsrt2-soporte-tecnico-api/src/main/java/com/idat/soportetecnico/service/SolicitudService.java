package com.idat.soportetecnico.service;

import com.idat.soportetecnico.model.EstadoSolicitud;
import com.idat.soportetecnico.model.Prioridad;
import com.idat.soportetecnico.model.Solicitud;


import java.util.List;

public interface SolicitudService {

    // Registrar una nueva solicitud
    Solicitud registrar(Solicitud solicitud);

    // Listar todas las solicitudes
    List<Solicitud> listar();

    // Buscar una solicitud por su ID
    Solicitud buscarPorId(Long id);

    // Actualizar una solicitud
    Solicitud actualizar(Long id, Solicitud solicitud);

    // Eliminar una solicitud
    void eliminar(Long id);

    // Buscar solicitudes por estado
    List<Solicitud> buscarPorEstado(EstadoSolicitud estado);

    // Buscar solicitudes por prioridad
    List<Solicitud> buscarPorPrioridad(Prioridad prioridad);
}