package com.idat.soportetecnico.repository;

import com.idat.soportetecnico.model.Tecnico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TecnicoRepository extends JpaRepository<Tecnico, Long> {

    List<Tecnico> findByDisponibleTrue();
}