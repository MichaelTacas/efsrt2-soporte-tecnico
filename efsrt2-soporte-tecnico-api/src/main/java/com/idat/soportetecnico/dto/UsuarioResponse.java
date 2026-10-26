package com.idat.soportetecnico.dto;

import com.idat.soportetecnico.model.RolUsuario;

public record UsuarioResponse(
        Long id,
        String email,
        RolUsuario rol,
        boolean activo
) {
}