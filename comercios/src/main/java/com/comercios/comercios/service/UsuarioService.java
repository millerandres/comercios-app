package com.comercios.comercios.service;

import com.comercios.comercios.entity.Usuario;

import java.util.Optional;
import java.util.List;

public interface UsuarioService {

    Optional<Usuario> obtenerPorCorreo(String correoElectronico);

    List<Usuario> listarUsuarios();

    Usuario guardar(Usuario usuario);
}