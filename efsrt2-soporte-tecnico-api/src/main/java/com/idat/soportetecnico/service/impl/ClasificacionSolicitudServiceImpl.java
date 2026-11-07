package com.idat.soportetecnico.service.impl;

import com.idat.soportetecnico.dto.ClasificarSolicitudRequest;
import com.idat.soportetecnico.dto.SolicitudResponse;
import com.idat.soportetecnico.exception.RecursoNoEncontradoException;
import com.idat.soportetecnico.exception.ReglaNegocioException;
import com.idat.soportetecnico.mapper.SolicitudMapper;
import com.idat.soportetecnico.model.EstadoSolicitud;
import com.idat.soportetecnico.model.Solicitud;
import com.idat.soportetecnico.model.Tecnico;
import com.idat.soportetecnico.repository.SolicitudRepository;
import com.idat.soportetecnico.repository.TecnicoRepository;
import com.idat.soportetecnico.service.ClasificacionSolicitudService;
import com.idat.soportetecnico.service.FechaLimiteService;
import com.idat.soportetecnico.validator.TecnicoDisponibleValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClasificacionSolicitudServiceImpl
        implements ClasificacionSolicitudService {

    private final SolicitudRepository solicitudRepository;
    private final TecnicoRepository tecnicoRepository;
    private final FechaLimiteService fechaLimiteService;
    private final TecnicoDisponibleValidator tecnicoDisponibleValidator;
    private final SolicitudMapper solicitudMapper;

    public ClasificacionSolicitudServiceImpl(
            SolicitudRepository solicitudRepository,
            TecnicoRepository tecnicoRepository,
            FechaLimiteService fechaLimiteService,
            TecnicoDisponibleValidator tecnicoDisponibleValidator,
            SolicitudMapper solicitudMapper
    ) {
        this.solicitudRepository = solicitudRepository;
        this.tecnicoRepository = tecnicoRepository;
        this.fechaLimiteService = fechaLimiteService;
        this.tecnicoDisponibleValidator = tecnicoDisponibleValidator;
        this.solicitudMapper = solicitudMapper;
    }

    @Override
    @Transactional
    public SolicitudResponse clasificarSolicitud(
            Long solicitudId,
            ClasificarSolicitudRequest request
    ) {

        Solicitud solicitud = solicitudRepository
                .findById(solicitudId)
                .orElseThrow(() ->
                        new RecursoNoEncontradoException(
                                "Solicitud no encontrada"
                        )
                );

        if (solicitud.getEstado() != EstadoSolicitud.PENDIENTE) {
            throw new ReglaNegocioException(
                    "Solo se pueden clasificar solicitudes pendientes"
            );
        }

        Tecnico tecnico = tecnicoRepository
                .findById(request.getTecnicoId())
                .orElseThrow(() ->
                        new RecursoNoEncontradoException(
                                "Técnico no encontrado"
                        )
                );

        // El administrador asigna la gravedad.
        solicitud.setGravedad(
                request.getGravedad()
        );

        // El administrador asigna el técnico.
        solicitud.setTecnico(
                tecnico
        );

        // Validamos que el técnico asignado esté disponible.
        tecnicoDisponibleValidator.validarRegistro(
                solicitud
        );

        // La fecha límite se calcula desde la fecha de creación
        // utilizando la gravedad asignada por el administrador.
        solicitud.setFechaLimite(
                fechaLimiteService.calcularFechaLimite(
                        solicitud.getFechaCreacion(),
                        request.getGravedad()
                )
        );

        Solicitud solicitudGuardada =
                solicitudRepository.save(solicitud);

        return solicitudMapper.toResponse(
                solicitudGuardada
        );
    }
}