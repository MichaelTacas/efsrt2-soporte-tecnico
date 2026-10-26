package com.idat.soportetecnico.service.impl;

import com.idat.soportetecnico.exception.RecursoNoEncontradoException;
import com.idat.soportetecnico.model.Tecnico;
import com.idat.soportetecnico.repository.TecnicoRepository;
import com.idat.soportetecnico.service.TecnicoService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TecnicoServiceImpl implements TecnicoService {

    private final TecnicoRepository tecnicoRepository;

    public TecnicoServiceImpl(TecnicoRepository tecnicoRepository) {
        this.tecnicoRepository = tecnicoRepository;
    }

    @Override
    public Tecnico registrar(Tecnico tecnico) {
        return tecnicoRepository.save(tecnico);
    }

    @Override
    public List<Tecnico> listar() {
        return tecnicoRepository.findAll();
    }

    @Override
    public Tecnico buscarPorId(Long id) {
        return tecnicoRepository.findById(id)
                .orElseThrow(() ->
                        new RecursoNoEncontradoException(
                                "Técnico no encontrado con id: " + id
                        )
                );
    }

    @Override
    public Tecnico actualizar(Long id, Tecnico tecnicoActualizado) {

        Tecnico tecnicoExistente = buscarPorId(id);

        tecnicoExistente.setNombre(tecnicoActualizado.getNombre());
        tecnicoExistente.setEspecialidad(tecnicoActualizado.getEspecialidad());
        tecnicoExistente.setDisponible(tecnicoActualizado.isDisponible());

        return tecnicoRepository.save(tecnicoExistente);
    }

    @Override
    public void eliminar(Long id) {

        Tecnico tecnico = buscarPorId(id);

        tecnicoRepository.delete(tecnico);
    }

    @Override
    public List<Tecnico> listarDisponibles() {
        return tecnicoRepository.findByDisponibleTrue();
    }
}