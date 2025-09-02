package com.comercios.comercios.dto;
import lombok.Data;
@Data
public class UsuarioDTO {
    private Long id;
    private String nombre;
    private String correoElectronico;
    private String rol;
}
