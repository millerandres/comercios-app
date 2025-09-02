package com.comercios.comercios.dto;
import com.comercios.comercios.entity.Rol;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@AllArgsConstructor
public class AuthResponse {
    @Getter
    private String token;
    private String rol;
}
