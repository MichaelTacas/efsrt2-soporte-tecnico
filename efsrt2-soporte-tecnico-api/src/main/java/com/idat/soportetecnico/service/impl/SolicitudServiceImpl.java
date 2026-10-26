package com.idat.soportetecnico.service.impl;

import com.idat.soportetecnico.exception.RecursoNoEncontradoException;
import com.idat.soportetecnico.model.EstadoSolicitud;
import com.idat.soportetecnico.model.Prioridad;
import com.idat.soportetecnico.model.Solicitud;
import com.idat.soportetecnico.repository.SolicitudRepository;
import com.idat.soportetecnico.service.SolicitudService;
import com.idat.soportetecnico.validator.SolicitudValidator;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SolicitudServiceImpl implements SolicitudService {

    private final SolicitudRepository solicitudRepository;
    private final List<SolicitudValidator> validadores;

    public SolicitudServiceImpl(
            SolicitudRepository solicitudRepository,
            List<SolicitudValidator> validadores
    ) {
        this.solicitudRepository = solicitudRepository;
        this.validadores = validadores;
    }

    @Override
    public Solicitud registrar(Solicitud solicitud) {

        validadores.forEach(
                validador -> validador.validarRegistro(solicitud)
        );

        solicitud.setEstado(EstadoSolicitud.PENDIENTE);

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

        solicitudExistente.setCliente(
                solicitudActualizada.getCliente()
        );

        solicitudExistente.setTecnico(
                solicitudActualizada.getTecnico()
        );

        if (solicitudActualizada.getEstado() != null) {
            solicitudExistente.setEstado(
                    solicitudActualizada.getEstado()
            );
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