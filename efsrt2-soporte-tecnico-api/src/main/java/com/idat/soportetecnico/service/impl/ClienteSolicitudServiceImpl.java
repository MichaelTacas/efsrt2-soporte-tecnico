package com.idat.soportetecnico.service.impl;

import com.idat.soportetecnico.dto.ClienteSolicitudRequest;
import com.idat.soportetecnico.dto.SolicitudResponse;
import com.idat.soportetecnico.exception.ReglaNegocioException;
import com.idat.soportetecnico.mapper.SolicitudMapper;
import com.idat.soportetecnico.model.Cliente;
import com.idat.soportetecnico.model.EstadoSolicitud;
import com.idat.soportetecnico.model.Solicitud;
import com.idat.soportetecnico.model.Usuario;
import com.idat.soportetecnico.repository.SolicitudRepository;
import com.idat.soportetecnico.repository.UsuarioRepository;
import com.idat.soportetecnico.service.ClienteSolicitudService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ClienteSolicitudServiceImpl
        implements ClienteSolicitudService {

    private final UsuarioRepository usuarioRepository;
    private final SolicitudRepository solicitudRepository;
    private final SolicitudMapper solicitudMapper;

    public ClienteSolicitudServiceImpl(
            UsuarioRepository usuarioRepository,
            SolicitudRepository solicitudRepository,
            SolicitudMapper solicitudMapper
    ) {
        this.usuarioRepository = usuarioRepository;
        this.solicitudRepository = solicitudRepository;
        this.solicitudMapper = solicitudMapper;
    }

    @Override
    public SolicitudResponse crearSolicitud(
            String emailUsuario,
            ClienteSolicitudRequest request
    ) {

        Cliente cliente = obtenerCliente(emailUsuario);

        Solicitud solicitud = new Solicitud();

        solicitud.setTitulo(
                request.getTitulo().trim()
        );

        solicitud.setDescripcion(
                request.getDescripcion().trim()
        );

        solicitud.setPrioridad(
                request.getPrioridad()
        );

        solicitud.setCliente(cliente);

        // El cliente no puede asignarse un técnico.
        solicitud.setTecnico(null);

        // La gravedad será definida posteriormente
        // por el administrador.
        solicitud.setGravedad(null);

        solicitud.setEstado(
                EstadoSolicitud.PENDIENTE
        );

        solicitud.setFechaCreacion(
                LocalDateTime.now()
        );

        // La fecha límite se calculará cuando
        // el administrador asigne la gravedad.
        solicitud.setFechaLimite(null);

        Solicitud guardada =
                solicitudRepository.save(solicitud);

        return solicitudMapper.toResponse(
                guardada
        );
    }

    @Override
    public List<SolicitudResponse> listarMisSolicitudes(
            String emailUsuario
    ) {

        Cliente cliente =
                obtenerCliente(emailUsuario);

        return solicitudRepository
                .findByClienteOrderByFechaCreacionDesc(
                        cliente
                )
                .stream()
                .map(solicitudMapper::toResponse)
                .toList();
    }

    private Cliente obtenerCliente(
            String emailUsuario
    ) {

        Usuario usuario = usuarioRepository
                .findByEmailIgnoreCase(emailUsuario)
                .orElseThrow(() ->
                        new ReglaNegocioException(
                                "No se encontró el usuario autenticado"
                        )
                );

        if (usuario.getCliente() == null) {

            throw new ReglaNegocioException(
                    "El usuario no tiene un cliente asociado"
            );
        }

        return usuario.getCliente();
    }
}