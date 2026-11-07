package com.idat.soportetecnico.service.impl;

import com.idat.soportetecnico.exception.RecursoNoEncontradoException;
import com.idat.soportetecnico.model.EstadoSolicitud;
import com.idat.soportetecnico.model.Prioridad;
import com.idat.soportetecnico.model.Solicitud;
import com.idat.soportetecnico.repository.SolicitudRepository;
import com.idat.soportetecnico.service.FechaLimiteService;
import com.idat.soportetecnico.service.SolicitudService;
import com.idat.soportetecnico.validator.SolicitudValidator;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SolicitudServiceImpl implements SolicitudService {

    private final SolicitudRepository solicitudRepository;
    private final List<SolicitudValidator> validadores;
    private final FechaLimiteService fechaLimiteService;

    public SolicitudServiceImpl(
            SolicitudRepository solicitudRepository,
            List<SolicitudValidator> validadores,
            FechaLimiteService fechaLimiteService
    ) {
        this.solicitudRepository = solicitudRepository;
        this.validadores = validadores;
        this.fechaLimiteService = fechaLimiteService;
    }

    @Override
    public Solicitud registrar(Solicitud solicitud) {

        validadores.forEach(
                validador -> validador.validarRegistro(solicitud)
        );

        solicitud.setEstado(EstadoSolicitud.PENDIENTE);

        LocalDateTime fechaCreacion = LocalDateTime.now();

        solicitud.setFechaCreacion(fechaCreacion);

        solicitud.setFechaLimite(
                fechaLimiteService.calcularFechaLimite(
                        fechaCreacion,
                        solicitud.getGravedad()
                )
        );

        return solicitudRepository.save(solicitud);
    }

    @Override
    public List<Solicitud> listar() {
        return solicitudRepository.findAll();
    }

    @Override
    public Solicitud buscarPorId(Long id) {

        return solicitudRepository.findById(id)
                .orElseThrow(() ->
                        new RecursoNoEncontradoException(
                                "Solicitud no encontrada con id: " + id
                        )
                );
    }

    @Override
    public Solicitud actualizar(
            Long id,
            Solicitud solicitudActualizada
    ) {

        Solicitud solicitudExistente = buscarPorId(id);

        validadores.forEach(
                validador -> validador.validarActualizacion(
                        solicitudExistente,
                        solicitudActualizada
                )
        );

        solicitudExistente.setTitulo(
                solicitudActualizada.getTitulo()
        );

        solicitudExistente.setDescripcion(
                solicitudActualizada.getDescripcion()
        );

        solicitudExistente.setPrioridad(
                solicitudActualizada.getPrioridad()
        );

        solicitudExistente.setGravedad(
                solicitudActualizada.getGravedad()
        );

        solicitudExistente.setCliente(
                solicitudActualizada.getCliente()
        );

        solicitudExistente.setTecnico(
                solicitudActualizada.getTecnico()
        );

        if (solicitudActualizada.getEstado() != null
                && solicitudActualizada.getEstado()
                != solicitudExistente.getEstado()) {

            EstadoSolicitud nuevoEstado =
                    solicitudActualizada.getEstado();

            LocalDateTime ahora = LocalDateTime.now();

            if (nuevoEstado == EstadoSolicitud.EN_PROCESO
                    && solicitudExistente.getFechaInicioAtencion() == null) {

                solicitudExistente.setFechaInicioAtencion(ahora);
            }

            if (nuevoEstado == EstadoSolicitud.RESUELTA
                    && solicitudExistente.getFechaResolucion() == null) {

                solicitudExistente.setFechaResolucion(ahora);
            }

            if (nuevoEstado == EstadoSolicitud.CERRADA
                    && solicitudExistente.getFechaCierre() == null) {

                solicitudExistente.setFechaCierre(ahora);
            }

            solicitudExistente.setEstado(nuevoEstado);
        }

        return solicitudRepository.save(solicitudExistente);
    }

    @Override
    public void eliminar(Long id) {

        Solicitud solicitud = buscarPorId(id);

        solicitudRepository.delete(solicitud);
    }

    @Override
    public List<Solicitud> buscarPorEstado(
            EstadoSolicitud estado
    ) {
        return solicitudRepository.findByEstado(estado);
    }

    @Override
    public List<Solicitud> buscarPorPrioridad(
            Prioridad prioridad
    ) {
        return solicitudRepository.findByPrioridad(prioridad);
    }
}