package com.comercios.comercios.controller;

import com.comercios.comercios.dto.ApiResponse;
import com.comercios.comercios.service.ComercianteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/municipios")
public class MunicipioController {

    private final ComercianteService comercianteService;

    public MunicipioController(ComercianteService comercianteService) {
        this.comercianteService = comercianteService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<String>>> obtenerMunicipios() {
        List<String> municipios = comercianteService.obtenerMunicipios();
        return ResponseEntity.ok(new ApiResponse<>(true, "Lista de municipios obtenida", municipios));
    }
}