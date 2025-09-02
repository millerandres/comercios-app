package com.comercios.comercios.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
@Data
@AllArgsConstructor
public class ComercianteRequest {

    @NotBlank
    private String nombre;

    @NotBlank
    private String municipio;

    private String telefono;

    @Email
    private String correo;

    @NotNull
    private LocalDate fechaRegistro;

    @NotBlank
    private String estado;

    // Getters y Setters
}
