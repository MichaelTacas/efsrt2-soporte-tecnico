package com.idat.soportetecnico.service;

import com.idat.soportetecnico.dto.ClienteSolicitudRequest;
import com.idat.soportetecnico.dto.SolicitudResponse;

import java.util.List;

public interface ClienteSolicitudService {

    SolicitudResponse crearSolicitud(
            String emailUsuario,
            ClienteSolicitudRequest request
    );

    List<SolicitudResponse> listarMisSolicitudes(
            String emailUsuario
    );
}