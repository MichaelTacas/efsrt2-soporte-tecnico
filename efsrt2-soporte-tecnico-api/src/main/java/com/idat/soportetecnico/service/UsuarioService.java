package com.idat.soportetecnico.service;

import com.idat.soportetecnico.dto.UsuarioRequest;
import com.idat.soportetecnico.dto.UsuarioResponse;

import java.util.List;

public interface UsuarioService {

    UsuarioResponse crear(UsuarioRequest request);

    List<UsuarioResponse> listar();
}