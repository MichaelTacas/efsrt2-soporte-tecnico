package com.idat.soportetecnico.service.impl;

import com.idat.soportetecnico.dto.ActualizarClienteRequest;
import com.idat.soportetecnico.dto.AdminClienteResponse;
import com.idat.soportetecnico.dto.RegistroClienteRequest;
import com.idat.soportetecnico.exception.ReglaNegocioException;
import com.idat.soportetecnico.model.Cliente;
import com.idat.soportetecnico.model.RolUsuario;
import com.idat.soportetecnico.model.Usuario;
import com.idat.soportetecnico.repository.ClienteRepository;
import com.idat.soportetecnico.repository.UsuarioRepository;
import com.idat.soportetecnico.service.RegistroClienteService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RegistroClienteServiceImpl implements RegistroClienteService {

    private final ClienteRepository clienteRepository;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public RegistroClienteServiceImpl(
            ClienteRepository clienteRepository,
            UsuarioRepository usuarioRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.clienteRepository = clienteRepository;
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public AdminClienteResponse registrarCliente(
            RegistroClienteRequest request
    ) {

        String email = request.getEmail()
                .trim()
                .toLowerCase();

        if (usuarioRepository.findByEmailIgnoreCase(email).isPresent()) {
            throw new ReglaNegocioException(
                    "Ya existe un usuario con este correo"
            );
        }

        if (clienteRepository.findByEmailIgnoreCase(email).isPresent()) {
            throw new ReglaNegocioException(
                    "Ya existe un cliente con este correo"
            );
        }

        Cliente cliente = new Cliente();

        cliente.setNombre(
                request.getNombre().trim()
        );

        cliente.setEmail(email);

        cliente.setEmpresa(
                request.getEmpresa().trim()
        );

        cliente.setArea(
                request.getArea().trim()
        );

        cliente.setTelefono(
                request.getTelefono().trim()
        );

        Cliente clienteGuardado =
                clienteRepository.save(cliente);

        Usuario usuario = new Usuario();

        usuario.setEmail(email);

        usuario.setPasswordHash(
                passwordEncoder.encode(
                        request.getPassword()
                )
        );

        usuario.setRol(
                RolUsuario.CLIENTE
        );

        usuario.setActivo(
                request.isActivo()
        );

        usuario.setCliente(
                clienteGuardado
        );

        usuarioRepository.save(usuario);

        return convertirRespuesta(
                clienteGuardado,
                usuario
        );
    }

    @Override
    public List<AdminClienteResponse> listarClientes() {

        return clienteRepository
                .findAll()
                .stream()
                .map(cliente -> {

                    Usuario usuario = usuarioRepository
                            .findByCliente_Id(cliente.getId())
                            .orElse(null);

                    boolean activo =
                            usuario != null
                                    && usuario.isActivo();

                    return new AdminClienteResponse(
                            cliente.getId(),
                            cliente.getNombre(),
                            cliente.getEmail(),
                            cliente.getEmpresa(),
                            cliente.getArea(),
                            cliente.getTelefono(),
                            cliente.getFechaCreacion(),
                            activo
                    );
                })
                .toList();
    }

    @Override
    @Transactional
    public AdminClienteResponse actualizarCliente(
            Long clienteId,
            ActualizarClienteRequest request
    ) {

        Cliente cliente =
                obtenerCliente(clienteId);

        Usuario usuario =
                obtenerUsuarioCliente(clienteId);

        String nuevoEmail = request
                .getEmail()
                .trim()
                .toLowerCase();

        if (!cliente.getEmail()
                .equalsIgnoreCase(nuevoEmail)) {

            if (usuarioRepository
                    .findByEmailIgnoreCase(nuevoEmail)
                    .isPresent()) {

                throw new ReglaNegocioException(
                        "Ya existe un usuario con este correo"
                );
            }

            if (clienteRepository
                    .findByEmailIgnoreCase(nuevoEmail)
                    .isPresent()) {

                throw new ReglaNegocioException(
                        "Ya existe un cliente con este correo"
                );
            }
        }

        cliente.setNombre(
                request.getNombre().trim()
        );

        cliente.setEmail(
                nuevoEmail
        );

        cliente.setEmpresa(
                request.getEmpresa().trim()
        );

        cliente.setArea(
                request.getArea().trim()
        );

        cliente.setTelefono(
                request.getTelefono().trim()
        );

        usuario.setEmail(
                nuevoEmail
        );

        clienteRepository.save(cliente);
        usuarioRepository.save(usuario);

        return convertirRespuesta(
                cliente,
                usuario
        );
    }

    @Override
    @Transactional
    public void cambiarEstado(
            Long clienteId,
            boolean activo
    ) {

        Usuario usuario =
                obtenerUsuarioCliente(clienteId);

        usuario.setActivo(activo);

        usuarioRepository.save(usuario);
    }

    @Override
    @Transactional
    public void restablecerPassword(
            Long clienteId,
            String nuevaPassword
    ) {

        Usuario usuario =
                obtenerUsuarioCliente(clienteId);

        usuario.setPasswordHash(
                passwordEncoder.encode(
                        nuevaPassword
                )
        );

        usuarioRepository.save(usuario);
    }

    private Cliente obtenerCliente(
            Long clienteId
    ) {

        return clienteRepository
                .findById(clienteId)
                .orElseThrow(() ->
                        new ReglaNegocioException(
                                "Cliente no encontrado"
                        )
                );
    }

    private Usuario obtenerUsuarioCliente(
            Long clienteId
    ) {

        return usuarioRepository
                .findByCliente_Id(clienteId)
                .orElseThrow(() ->
                        new ReglaNegocioException(
                                "El cliente no tiene una cuenta asociada"
                        )
                );
    }

    private AdminClienteResponse convertirRespuesta(
            Cliente cliente,
            Usuario usuario
    ) {

        return new AdminClienteResponse(
                cliente.getId(),
                cliente.getNombre(),
                cliente.getEmail(),
                cliente.getEmpresa(),
                cliente.getArea(),
                cliente.getTelefono(),
                cliente.getFechaCreacion(),
                usuario.isActivo()
        );
    }
}
