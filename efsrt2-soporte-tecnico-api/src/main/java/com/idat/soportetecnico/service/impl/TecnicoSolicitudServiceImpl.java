package com.idat.soportetecnico.service.impl;

import com.idat.soportetecnico.dto.SolicitudResponse;
import com.idat.soportetecnico.exception.RecursoNoEncontradoException;
import com.idat.soportetecnico.exception.ReglaNegocioException;
import com.idat.soportetecnico.mapper.SolicitudMapper;
import com.idat.soportetecnico.model.EstadoSolicitud;
import com.idat.soportetecnico.model.Solicitud;
import com.idat.soportetecnico.model.Tecnico;
import com.idat.soportetecnico.model.Usuario;
import com.idat.soportetecnico.repository.SolicitudRepository;
import com.idat.soportetecnico.repository.UsuarioRepository;
import com.idat.soportetecnico.service.TecnicoSolicitudService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TecnicoSolicitudServiceImpl
        implements TecnicoSolicitudService {

    private final UsuarioRepository usuarioRepository;
    private final SolicitudRepository solicitudRepository;
    private final SolicitudMapper solicitudMapper;

    public TecnicoSolicitudServiceImpl(
            UsuarioRepository usuarioRepository,
            SolicitudRepository solicitudRepository,
            SolicitudMapper solicitudMapper
    ) {
        this.usuarioRepository = usuarioRepository;
        this.solicitudRepository = solicitudRepository;
        this.solicitudMapper = solicitudMapper;
    }

    @Override
    public List<SolicitudResponse> listarMisSolicitudes(
            String emailUsuario
    ) {

        Tecnico tecnico = obtenerTecnico(emailUsuario);

        return solicitudRepository
                .findByTecnicoOrderByFechaCreacionDesc(tecnico)
                .stream()
                .map(solicitudMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public SolicitudResponse iniciarAtencion(
            String emailUsuario,
            Long solicitudId
    ) {

        Tecnico tecnico = obtenerTecnico(emailUsuario);

        Solicitud solicitud =
                obtenerSolicitudAsignada(
                        solicitudId,
                        tecnico
                );

        if (solicitud.getEstado()
                != EstadoSolicitud.PENDIENTE) {

            throw new ReglaNegocioException(
                    "Solo se puede iniciar una solicitud pendiente"
            );
        }

        solicitud.setEstado(
                EstadoSolicitud.EN_PROCESO
        );

        solicitud.setFechaInicioAtencion(
                LocalDateTime.now()
        );

        Solicitud guardada =
                solicitudRepository.save(solicitud);

        return solicitudMapper.toResponse(guardada);
    }

    @Override
    @Transactional
    public SolicitudResponse resolverSolicitud(
            String emailUsuario,
            Long solicitudId
    ) {

        Tecnico tecnico = obtenerTecnico(emailUsuario);

        Solicitud solicitud =
                obtenerSolicitudAsignada(
                        solicitudId,
                        tecnico
                );

        if (solicitud.getEstado()
                != EstadoSolicitud.EN_PROCESO) {

            throw new ReglaNegocioException(
                    "Solo se puede resolver una solicitud en proceso"
            );
        }

        solicitud.setEstado(
                EstadoSolicitud.RESUELTA
        );

        solicitud.setFechaResolucion(
                LocalDateTime.now()
        );

        Solicitud guardada =
                solicitudRepository.save(solicitud);

        return solicitudMapper.toResponse(guardada);
    }

    private Tecnico obtenerTecnico(
            String emailUsuario
    ) {

        Usuario usuario = usuarioRepository
                .findByEmailIgnoreCase(emailUsuario)
                .orElseThrow(() ->
                        new ReglaNegocioException(
                                "No se encontró el usuario autenticado"
                        )
                );

        if (usuario.getTecnico() == null) {

            throw new ReglaNegocioException(
                    "El usuario no tiene un técnico asociado"
            );
        }

        return usuario.getTecnico();
    }

    private Solicitud obtenerSolicitudAsignada(
            Long solicitudId,
            Tecnico tecnico
    ) {

        Solicitud solicitud =
                solicitudRepository
                        .findById(solicitudId)
                        .orElseThrow(() ->
                                new RecursoNoEncontradoException(
                                        "Solicitud no encontrada con id: "
                                                + solicitudId
                                )
                        );

        if (solicitud.getTecnico() == null
                || !solicitud.getTecnico()
                .getId()
                .equals(tecnico.getId())) {

            throw new ReglaNegocioException(
                    "La solicitud no está asignada al técnico autenticado"
            );
        }

        return solicitud;
    }
}