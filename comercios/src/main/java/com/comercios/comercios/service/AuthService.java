package com.comercios.comercios.service;

import com.comercios.comercios.entity.Usuario;
import com.comercios.comercios.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    private final UsuarioService usuarioService;

    public AuthService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder, UsuarioService usuarioService) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.usuarioService = usuarioService;
    }

    public Usuario login(String correo, String contrasena) {
        Usuario usuario = usuarioRepository.findByCorreoElectronico(correo)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Con NoOpPasswordEncoder puedes comparar directamente
        if (!usuario.getContrasena().equals(contrasena)) {
            throw new RuntimeException("Contraseña incorrecta");
        }

        return usuario;
    }

    public Optional<Usuario> validarCredenciales(String correo, String contrasena) {
        Optional<Usuario> usuarioOpt = usuarioService.obtenerPorCorreo(correo);

        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();

            if (usuario.getContrasena().equals(contrasena)) {
                return usuarioOpt;  // credenciales válidas
            }
        }

        return Optional.empty();  // credenciales inválidas
    }
}
