package com.idat.soportetecnico.service;

import com.idat.soportetecnico.dto.ActualizarClienteRequest;
import com.idat.soportetecnico.dto.AdminClienteResponse;
import com.idat.soportetecnico.dto.RegistroClienteRequest;

import java.util.List;

public interface RegistroClienteService {

    AdminClienteResponse registrarCliente(
            RegistroClienteRequest request
    );

    List<AdminClienteResponse> listarClientes();

    AdminClienteResponse actualizarCliente(
            Long clienteId,
            ActualizarClienteRequest request
    );

    void cambiarEstado(
            Long clienteId,
            boolean activo
    );

    void restablecerPassword(
            Long clienteId,
            String nuevaPassword
    );
}