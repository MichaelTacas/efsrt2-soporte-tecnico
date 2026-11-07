package com.idat.soportetecnico.service;

import com.idat.soportetecnico.dto.ClasificarSolicitudRequest;
import com.idat.soportetecnico.dto.SolicitudResponse;

public interface ClasificacionSolicitudService {

    SolicitudResponse clasificarSolicitud(
            Long solicitudId,
            ClasificarSolicitudRequest request
    );
}