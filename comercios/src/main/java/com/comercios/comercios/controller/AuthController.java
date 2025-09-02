package com.comercios.comercios.controller;

import com.comercios.comercios.dto.AuthResponse;
import com.comercios.comercios.dto.LoginRequest;
import com.comercios.comercios.entity.Usuario;
import com.comercios.comercios.security.JwtUtil;
import com.comercios.comercios.service.AuthService;
import com.comercios.comercios.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UsuarioService usuarioService;

    private final AuthService authService;
    private final JwtUtil jwtUtil;

    public AuthController(UsuarioService usuarioService, AuthService authService, JwtUtil jwtUtil) {
        this.usuarioService = usuarioService;
        this.authService = authService;
        this.jwtUtil = jwtUtil;
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        String correo = loginRequest.getCorreoElectronico();
        String contrasena = loginRequest.getContrasena();

        // 🔑 Validación de prueba (esto deberías cambiarlo por una consulta a tu BD)
        if ("carlos.admin@mail.com".equals(correo) && "admin123".equals(contrasena)) {
            String rol = "Administrador";
            String token = jwtUtil.generateToken(correo, rol);

            AuthResponse response = new AuthResponse(token, rol);
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas");
    }
}
