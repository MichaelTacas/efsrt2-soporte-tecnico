package com.idat.soportetecnico.service.impl;

import com.idat.soportetecnico.dto.UsuarioRequest;
import com.idat.soportetecnico.dto.UsuarioResponse;
import com.idat.soportetecnico.exception.ReglaNegocioException;
import com.idat.soportetecnico.model.Usuario;
import com.idat.soportetecnico.repository.UsuarioRepository;
import com.idat.soportetecnico.service.UsuarioService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioServiceImpl(
            UsuarioRepository usuarioRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public UsuarioResponse crear(UsuarioRequest request) {

        String email = request.email()
                .trim()
                .toLowerCase(Locale.ROOT);

        if (usuarioRepository.existsByEmailIgnoreCase(email)) {
            throw new ReglaNegocioException(
                    "Ya existe un usuario con ese correo"
            );
        }

        Usuario usuario = new Usuario();

        usuario.setEmail(email);
        usuario.setPasswordHash(
                passwordEncoder.encode(request.password())
        );
        usuario.setRol(request.rol());
        usuario.setActivo(true);

        Usuario guardado =
                usuarioRepository.save(usuario);

        return convertirRespuesta(guardado);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UsuarioResponse> listar() {

        return usuarioRepository.findAll()
                .stream()
                .map(this::convertirRespuesta)
                .toList();
    }

    private UsuarioResponse convertirRespuesta(
            Usuario usuario
    ) {

        return new UsuarioResponse(
                usuario.getId(),
                usuario.getEmail(),
                usuario.getRol(),
                usuario.isActivo()
        );
    }
}