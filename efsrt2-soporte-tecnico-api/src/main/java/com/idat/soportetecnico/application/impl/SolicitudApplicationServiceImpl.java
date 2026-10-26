package com.idat.soportetecnico.application.impl;

import com.idat.soportetecnico.application.SolicitudApplicationService;
import com.idat.soportetecnico.dto.SolicitudRequest;
import com.idat.soportetecnico.dto.SolicitudResponse;
import com.idat.soportetecnico.mapper.SolicitudMapper;
import com.idat.soportetecnico.model.Cliente;
import com.idat.soportetecnico.model.EstadoSolicitud;
import com.idat.soportetecnico.model.Prioridad;
import com.idat.soportetecnico.model.Solicitud;
import com.idat.soportetecnico.model.Tecnico;
import com.idat.soportetecnico.service.ClienteService;
import com.idat.soportetecnico.service.SolicitudService;
import com.idat.soportetecnico.service.TecnicoService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SolicitudApplicationServiceImpl
        implements SolicitudApplicationService {

    private final SolicitudService solicitudService;
    private final ClienteService clienteService;
    private final TecnicoService tecnicoService;
    private final SolicitudMapper solicitudMapper;

    public SolicitudApplicationServiceImpl(
            SolicitudService solicitudService,
            ClienteService clienteService,
            TecnicoService tecnicoService,
            SolicitudMapper solicitudMapper
    ) {
        this.solicitudService = solicitudService;
        this.clienteService = clienteService;
        this.tecnicoService = tecnicoService;
        this.solicitudMapper = solicitudMapper;
    }

    @Override
    public SolicitudResponse registrar(SolicitudRequest request) {

        Cliente cliente =
                clienteService.buscarPorId(request.getClienteId());

        Tecnico tecnico =
                obtenerTecnico(request.getTecnicoId());

        Solicitud solicitud =
                solicitudMapper.toEntity(
                        request,
                        cliente,
                        tecnico
                );

        Solicitud solicitudGuardada =
                solicitudService.registrar(solicitud);

        return solicitudMapper.toResponse(solicitudGuardada);
    }

    @Override
    public List<SolicitudResponse> listar() {

        return solicitudService.listar()
                .stream()
                .map(solicitudMapper::toResponse)
                .toList();
    }

    @Override
    public SolicitudResponse buscarPorId(Long id) {

        Solicitud solicitud =
                solicitudService.buscarPorId(id);

        return solicitudMapper.toResponse(solicitud);
    }

    @Override
    public SolicitudResponse actualizar(
            Long id,
            SolicitudRequest request
    ) {

        Cliente cliente =
                clienteService.buscarPorId(request.getClienteId());

        Tecnico tecnico =
                obtenerTecnico(request.getTecnicoId());

        Solicitud solicitud =
                solicitudMapper.toEntity(
                        request,
                        cliente,
                        tecnico
                );

        Solicitud solicitudActualizada =
                solicitudService.actualizar(
                        id,
                        solicitud
                );

        return solicitudMapper.toResponse(
                solicitudActualizada
        );
    }

    @Override
    public void eliminar(Long id) {
        solicitudService.eliminar(id);
    }

    @Override
    public List<SolicitudResponse> buscarPorEstado(
            EstadoSolicitud estado
    ) {

        return solicitudService.buscarPorEstado(estado)
                .stream()
                .map(solicitudMapper::toResponse)
                .toList();
    }

    @Override
    public List<SolicitudResponse> buscarPorPrioridad(
            Prioridad prioridad
    ) {

        return solicitudService.buscarPorPrioridad(prioridad)
                .stream()
                .map(solicitudMapper::toResponse)
                .toList();
    }

    private Tecnico obtenerTecnico(Long tecnicoId) {

        if (tecnicoId == null) {
            return null;
        }

        return tecnicoService.buscarPorId(tecnicoId);
    }
}