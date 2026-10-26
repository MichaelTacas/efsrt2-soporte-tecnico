package com.idat.soportetecnico.application;

import com.idat.soportetecnico.dto.SolicitudRequest;
import com.idat.soportetecnico.dto.SolicitudResponse;
import com.idat.soportetecnico.model.EstadoSolicitud;
import com.idat.soportetecnico.model.Prioridad;

import java.util.List;

public interface SolicitudApplicationService {

    SolicitudResponse registrar(SolicitudRequest request);

    List<SolicitudResponse> listar();

    SolicitudResponse buscarPorId(Long id);

    SolicitudResponse actualizar(
            Long id,
            SolicitudRequest request
    );

    void eliminar(Long id);

    List<SolicitudResponse> buscarPorEstado(
            EstadoSolicitud estado
    );

    List<SolicitudResponse> buscarPorPrioridad(
            Prioridad prioridad
    );
}