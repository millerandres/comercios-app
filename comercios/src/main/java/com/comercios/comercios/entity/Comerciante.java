package com.comercios.comercios.entity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "COMERCIANTE", schema = "MILLERCARO")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Comerciante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_COMERCIANTE")
    private Long id;

    @Column(name = "NOMBRE_RAZON_SOCIAL", nullable = false)
    private String nombreRazonSocial;

    @Column(name = "MUNICIPIO", nullable = false)
    private String municipio;

    @Column(name = "TELEFONO")
    private String telefono;

    @Column(name = "CORREO_ELECTRONICO")
    private String correoElectronico;

    @Column(name = "FECHA_REGISTRO")
    private LocalDate fechaRegistro;

    @Column(name = "ESTADO")
    private String estado;

    @Column(name = "FECHA_ACTUALIZACION")
    private LocalDate fechaActualizacion;

    @Column(name = "USUARIO_ACTUALIZACION")
    private String usuarioActualizacion;
}
