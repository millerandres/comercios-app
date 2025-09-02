package com.comercios.comercios.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "USUARIO", schema = "MILLERCARO")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usuario_seq")
    @SequenceGenerator(name = "usuario_seq", sequenceName = "USUARIO_SEQ", allocationSize = 1)
    @Column(name = "id_usuario")
    private Long idUsuario;

    @Column(name = "NOMBRE", nullable = false)
    private String nombre;

    @Column(name = "CORREO_ELECTRONICO", nullable = false, unique = true)
    private String correoElectronico;

    @Column(name = "CONTRASENA", nullable = false)
    private String contrasena;

    @Enumerated(EnumType.STRING)
    @Column(name = "ROL", nullable = false)
    private Rol rol;

    @Column(name = "FECHA_ACTUALIZACION")
    private LocalDateTime fechaActualizacion;

    @Column(name = "USUARIO_ACTUALIZACION")
    private String usuarioActualizacion;
}