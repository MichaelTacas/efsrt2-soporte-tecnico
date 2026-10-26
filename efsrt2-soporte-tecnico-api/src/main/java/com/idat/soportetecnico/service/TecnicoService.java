package com.idat.soportetecnico.service;

import com.idat.soportetecnico.model.Tecnico;

import java.util.List;

public interface TecnicoService {

    Tecnico registrar(Tecnico tecnico);

    List<Tecnico> listar();

    Tecnico buscarPorId(Long id);

    Tecnico actualizar(Long id, Tecnico tecnico);

    void eliminar(Long id);

    List<Tecnico> listarDisponibles();
}