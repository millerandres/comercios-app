package com.comercios.comercios.controller;

import com.comercios.comercios.dto.ApiResponse;
import com.comercios.comercios.dto.ComercianteRequest;
import com.comercios.comercios.entity.Comerciante;
import com.comercios.comercios.service.ComercianteService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;

@RestController
@RequestMapping("/api/comerciantes")
@SecurityRequirement(name = "bearerAuth") // Swagger + JWT
public class ComercianteController {

    private final ComercianteService service;

    public ComercianteController(ComercianteService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<Comerciante>>> listar(
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaRegistro,
            @RequestParam(required = false) String estado,
            @PageableDefault(size = 5) Pageable pageable
    ) {
        return ResponseEntity.ok(
                new ApiResponse<>(true, "Listado paginado", service.listarPaginado(nombre, fechaRegistro, estado, pageable))
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Comerciante>> obtenerPorId(@PathVariable Long id) {
        Comerciante c = service.obtenerPorId(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Comerciante encontrado", c));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Comerciante>> crear(@RequestBody @Valid ComercianteRequest request, Principal principal) {
        return ResponseEntity.ok(
                new ApiResponse<>(true, "Creado con éxito", service.crear(request, principal.getName()))
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Comerciante>> actualizar(@PathVariable Long id, @RequestBody @Valid ComercianteRequest request, Principal principal) {
        return ResponseEntity.ok(
                new ApiResponse<>(true, "Actualizado con éxito", service.actualizar(id, request, principal.getName()))
        );
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<ApiResponse<Comerciante>> cambiarEstado(@PathVariable Long id, @RequestParam String estado, Principal principal) {
        return ResponseEntity.ok(
                new ApiResponse<>(true, "Estado modificado", service.cambiarEstado(id, estado, principal.getName()))
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Eliminado con éxito", null));
    }
    @PreAuthorize("hasRole('ADMIN')") // Solo usuarios con rol ADMIN
    @GetMapping("/exportar/activos")
    public ResponseEntity<byte[]> exportarComerciantesActivos() {
        byte[] csv = service.generarCsvComerciantesActivosDesdeFuncion();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDisposition(ContentDisposition
                .attachment()
                .filename("comerciantes_activos.csv")
                .build());

        return ResponseEntity.ok().headers(headers).body(csv);
    }
}