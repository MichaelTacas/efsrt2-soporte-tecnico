package com.idat.soportetecnico.dto;

import com.idat.soportetecnico.model.RolUsuario;

public record AuthResponse(

        String token,
        String tipo,
        long expiraEnSegundos,
        Long usuarioId,
        String email,
        RolUsuario rol

) {
}