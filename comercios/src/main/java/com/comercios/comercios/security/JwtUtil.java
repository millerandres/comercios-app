package com.comercios.comercios.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

import static javax.crypto.Cipher.SECRET_KEY;

@Component
public class JwtUtil {

    // Clave secreta generada de forma segura (solo debe inicializarse una vez)
    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    // Generar token con email + rol
    public String generateToken(String email, String rol) {
        return Jwts.builder()
                .setSubject(email)
                .claim("rol", rol)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 3600000)) // 1 hora
                .signWith(key)
                .compact();
    }

    // Extraer username del token
    public String getUsernameFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // Extraer rol del token
    public String getRoleFromToken(String token) {
        return (String) Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(token)
                .getBody()
                .get("rol");
    }

    // Validar token con el usuario
    public boolean validarToken(String token, String username) {
        String usuarioExtraidoDelToken = getUsernameFromToken(token);
        return usuarioExtraidoDelToken.equals(username) && !estaExpirado(token);
    }

    // Validar expiración
    private boolean estaExpirado(String token) {
        Date expiracion = Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
        return expiracion.before(new Date());
    }
}