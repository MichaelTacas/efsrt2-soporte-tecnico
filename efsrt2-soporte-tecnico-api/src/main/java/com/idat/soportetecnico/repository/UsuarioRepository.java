package com.idat.soportetecnico.repository;

import com.idat.soportetecnico.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository
        extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByEmailIgnoreCase(String email);

    Optional<Usuario> findByEmail(String email);

    boolean existsByEmailIgnoreCase(String email);

    Optional<Usuario> findByCliente_Id(Long clienteId);
}