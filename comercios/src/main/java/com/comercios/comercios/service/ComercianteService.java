package com.comercios.comercios.service;

import com.comercios.comercios.repository.ComercianteRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ComercianteService {

    private final ComercianteRepository comercianteRepository;

    public ComercianteService(ComercianteRepository comercianteRepository) {
        this.comercianteRepository = comercianteRepository;
    }

    @Cacheable("municipios") // Cache opcional
    public List<String> obtenerMunicipios() {
        return comercianteRepository.obtenerMunicipiosUnicos();
    }
}