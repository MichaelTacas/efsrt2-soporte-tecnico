package com.idat.soportetecnico.service;

import com.idat.soportetecnico.dto.SolicitudResponse;

import java.util.List;

public interface TecnicoSolicitudService {

    List<SolicitudResponse> listarMisSolicitudes(
            String emailUsuario
    );

    SolicitudResponse iniciarAtencion(
            String emailUsuario,
            Long solicitudId
    );

    SolicitudResponse resolverSolicitud(
            String emailUsuario,
            Long solicitudId
    );
}