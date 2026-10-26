package com.idat.soportetecnico.repository;

import com.idat.soportetecnico.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}